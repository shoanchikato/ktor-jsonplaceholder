package com.example.app.routing

import com.example.app.model.User
import com.example.app.repository.UserRepository
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get


fun Routing.userRouting() {

    //    val userRepository: UserRepository by inject()
    val userRepository: UserRepository = get()

    route("/users") {
        get("") {
            call.respond(userRepository.getAll())
        }

        get("{id}") {
            val id = call.parameters.get("id")?.toInt() ?: -1
            call.respond(userRepository.getById(id = id))
        }

        post("") {
            val userDao = call.receive<User>()
            call.respond(userRepository.create(user = userDao))
        }

        put("{id}") {
            val id = call.parameters.get("id")?.toInt() ?: -1
            val user = call.receive<User>()
            call.respond(userRepository.edit(user = user, id = id))
        }
    }
}