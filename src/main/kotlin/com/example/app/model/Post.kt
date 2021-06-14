package com.example.app.model

import com.example.app.db.dao.PostDao

data class Post(
        val id: Int,
        val userId: Int,
        val title: String,
        val body: String
) {
    fun toPostDao() = PostDao(
            id = this.id,
            userId = this.userId,
            title = this.title,
            body = this.body
    )
}