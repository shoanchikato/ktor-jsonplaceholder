package com.example.app.di

import com.example.app.db.DB
import com.example.app.db.DBConnection
import com.example.app.model.Post
import com.example.app.model.User
import com.example.app.repository.PostRepository
import com.example.app.repository.PostRepositoryImpl
import com.example.app.repository.UserRepository
import com.example.app.repository.UserRepositoryImpl
import com.example.app.request.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
//    single { RepositoryImpl() } bind Repository::class // for matching RepositoryImpl and Repository at the same time
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<FetchRequests<Post>>(named("post-requests")) { PostRequestService(get()) }
    single<FetchRequests<User>>(named("user-requests")) { UserRequestService(get()) }

    single<DB> { DBConnection }
    single<HttpClientService> { KtorClientService }
}