package com.example.app.request

import com.example.app.model.Post
import io.ktor.client.request.*

class PostRequestService(
        private val httpClientService: HttpClientService
) : FetchRequests<Post> {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        const val PATH_URL = "posts"
    }

    override suspend fun getAll(): List<Post> {

        val url = "$BASE_URL/$PATH_URL"

        return httpClientService.httpClient.get<List<Post>>(url)
    }

}