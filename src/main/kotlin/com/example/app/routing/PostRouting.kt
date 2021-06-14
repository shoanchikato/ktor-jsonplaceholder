package com.example.app.routing

import com.example.app.db.dao.PostDao
import com.example.app.model.Post
import com.example.app.repository.PostRepository
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

@Location("{id}")
data class GetId(val id: Int)

fun Routing.postRouting() {

    //    val postRepository: PostRepository by inject()
    val postRepository: PostRepository = get()

    route("/posts") {
        get("") {
            call.respond(postRepository.getAll())
        }

        get<GetId> { it ->
//            val id = call.parameters.get("id")?.toInt() ?: -1
            call.respond(postRepository.getById(it.id))
        }

        post("") {
            val postDao = call.receive<PostDao>()
            call.respond(postRepository.create(post = postDao))
        }

        put("{id}") {
            val id = call.parameters.get("id")?.toInt() ?: -1
            val post = call.receive<Post>()
            call.respond(postRepository.edit(post = post, id = id))
        }
    }
}