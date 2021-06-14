package com.example.app.db.table

import org.jetbrains.exposed.sql.*
import com.example.app.model.Post
import com.example.app.db.dao.PostDao
import org.jetbrains.exposed.sql.statements.InsertStatement


object Posts : Table() {
    val userId = integer("userId")
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val body = varchar("body", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Posts_ID")

//    fun toPost(row: ResultRow): Post = Post(
//            id = row[Posts.id],
//            userId = row[Posts.userId],
//            title = row[Posts.title],
//            body = row[Posts.body],
//    )
}


fun ResultRow.toPost() = Post(
        id = this[Posts.id],
        userId = this[Posts.userId],
        title = this[Posts.title],
        body = this[Posts.body],
)

