package com.example.app.request

interface FetchRequests<T> {
    suspend fun getAll(): List<T>
}