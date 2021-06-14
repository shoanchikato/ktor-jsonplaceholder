package com.example.app.request

import com.example.app.model.User
import io.ktor.client.request.*

class UserRequestService(
        private val httpClientService: HttpClientService,
) : FetchRequests<User> {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        const val PATH_URL = "users"
    }

    override suspend fun getAll(): List<User> {

        val url = "$BASE_URL/$PATH_URL"

        return httpClientService.httpClient.get<List<User>>(url)
    }

}