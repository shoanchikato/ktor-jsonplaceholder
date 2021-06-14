package com.example.app.repository

import com.example.app.db.DB
import com.example.app.db.table.Addresses
import com.example.app.db.table.Geos
import com.example.app.db.table.Users
import com.example.app.db.table.toUser
import com.example.app.model.User
import com.example.app.request.FetchRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface UserRepository {
    fun create(user: User): User
    fun edit(user: User, id: Int): User
    fun getById(id: Int): User
    fun getAll(): List<User>
}

class UserRepositoryImpl(
        private val dbConnection: DB,
) : UserRepository, KoinComponent {

    private val userRequestService: FetchRequests<User> by inject(named("user-requests"))

    private fun populateDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = userRequestService.getAll()
            users.map {
                create(it)
            }
        }
    }

    init {
        populateDB()
    }

    val db = dbConnection.db

    override fun create(user: User): User = transaction {

        val geoId = Geos.insert {
            it[lat] = user.address.geo.lat
            it[lng] = user.address.geo.lng

        } get Geos.id

        val addressId = Addresses.insert {
            it[street] = user.address.street
            it[suite] = user.address.suite
            it[city] = user.address.city
            it[zipcode] = user.address.zipcode
            it[geo_id] = geoId

        } get Addresses.id

        val userId = Users.insert {
            it[name] = user.name
            it[username] = user.username
            it[email] = user.email
            it[address_id] = addressId

        } get Users.id

        Users.innerJoin(Addresses).innerJoin(Geos).select { Users.id eq userId }.single().toUser()
    }

    override fun edit(user: User, id: Int): User = transaction {

        val (addressId, geoId) = Users
                .innerJoin(Addresses)
                .innerJoin(Geos)
                .select { Users.id eq id }
                .single()
                .let {
                    val addressId = it[Addresses.id]
                    val geoId = it[Geos.id]
                    Pair(addressId, geoId)
                }

        Geos.update({ Geos.id eq geoId }) {
            it[lat] = user.address.geo.lat
            it[lng] = user.address.geo.lng
        }

        Addresses.update({ Addresses.id eq addressId }) {
            it[street] = user.address.street
            it[suite] = user.address.suite
            it[city] = user.address.city
            it[zipcode] = user.address.zipcode
        }

        Users.update({ Users.id eq id }) {
            it[name] = user.name
            it[username] = user.username
            it[email] = user.email
            it[address_id] = addressId
        }

        Users.innerJoin(Addresses).innerJoin(Geos).select { Users.id eq id }.single().toUser()
    }

    override fun getById(id: Int): User = transaction {
        Users.innerJoin(Addresses).innerJoin(Geos).select { Users.id eq id }.single().toUser()
    }

    override fun getAll(): List<User> = transaction {
        Users.innerJoin(Addresses).innerJoin(Geos).selectAll().map {
            it.toUser()
        }
    }

}