package com.example.app.db.table

import com.example.app.model.Address
import com.example.app.model.Geo
import com.example.app.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val username = varchar("username", 255)
    val email = varchar("email", 255)
    val address_id = (integer("address_id") references Addresses.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Users_ID")
}

fun ResultRow.toUser() = User(
        id = this[Users.id],
        name = this[Users.name],
        username = this[Users.username],
        email = this[Users.email],
        address = toAddress(),
)

object Addresses : Table() {
    val id = integer("id").autoIncrement()
    val street = varchar("street", 255)
    val suite = varchar("suite", 255)
    val city = varchar("city", 255)
    val zipcode = varchar("zipcode", 255)
    val geo_id = (integer("geo_id") references Geos.id).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Addresses_ID")
}

fun ResultRow.toAddress() = Address(
        street = this[Addresses.street],
        suite = this[Addresses.suite],
        city = this[Addresses.city],
        zipcode = this[Addresses.zipcode],
        geo = toGeo(),
)

object Geos : Table() {
    val id = integer("id").autoIncrement()
    val lat = varchar("lat", 255)
    val lng = varchar("lng", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Geos_ID")
}

fun ResultRow.toGeo() = Geo(
        lat = this[Geos.lat],
        lng = this[Geos.lng],
)
