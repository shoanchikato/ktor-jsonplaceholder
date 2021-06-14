package com.example.app.routing

import com.example.app.db.dao.PostDao
import com.example.app.model.Post
import com.example.app.repository.PostRepository
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

//import org.koin.ktor.ext.inject

fun Routing.mainRouting() {

    get("/") {
        call.respondText("hello world")
    }

}