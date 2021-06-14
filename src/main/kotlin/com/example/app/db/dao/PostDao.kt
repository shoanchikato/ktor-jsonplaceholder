package com.example.app.db.dao

data class PostDao(
        val id: Int? = null,
        val userId: Int,
        val title: String,
        val body: String
)