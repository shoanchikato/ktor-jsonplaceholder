package com.example.app.repository

import com.example.app.db.DB
import com.example.app.db.dao.PostDao
import com.example.app.db.table.Posts
import com.example.app.db.table.toPost
import com.example.app.model.Post
import com.example.app.request.FetchRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface PostRepository {
    fun create(post: PostDao): Post
    fun edit(post: Post, id: Int): Post
    fun getById(id: Int): Post
    fun getAll(): List<Post>
}

class PostRepositoryImpl(
        private val dbConnection: DB,
) : PostRepository, KoinComponent {

    private val postRequestService: FetchRequests<Post> by inject(named("post-requests"))

    private fun populateDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val posts: List<Post> = postRequestService.getAll()
            posts.map {
                create(it.toPostDao())
            }
        }
    }

    init {
        populateDB()
    }

    val db = dbConnection.db

    override fun create(post: PostDao): Post = transaction {
        val id = Posts.insert {
            it[userId] = post.userId
            it[title] = post.title
            it[body] = post.body

        } get Posts.id

        Posts.select { Posts.id eq id }.single().toPost()
    }

    override fun edit(post: Post, id: Int): Post = transaction {

        Posts.update({ Posts.id eq id }) {
            it[userId] = post.userId
            it[title] = post.title
            it[body] = post.body
        }

        Posts.select { Posts.id eq id }.single().toPost()
    }

    override fun getById(id: Int): Post = transaction {
        Posts.select { Posts.id eq id }.single().toPost()
    }

    override fun getAll(): List<Post> = transaction {
        Posts.selectAll().map {
            it.toPost()
        }
    }

}