package com.example.app.db

import com.example.app.db.table.Addresses
import com.example.app.db.table.Geos
import com.example.app.db.table.Posts
import com.example.app.db.table.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction


interface DB {
    val db: Any
}

object DBConnection : DB {

    private val config = HikariConfig().apply {
        jdbcUrl = "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;"
        driverClassName = "org.h2.Driver"
        username = "root"
        password = ""
        maximumPoolSize = 10
    }
    private val dataSource = HikariDataSource(config)

//    val db = Database.connect(
//            url = "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",
//            driver = "org.h2.Driver",
//            user = "root",
//            password = ""
//    )

    override val db: Database = Database.connect(dataSource)

    init {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Posts, Users, Addresses, Geos)
        }
    }
}