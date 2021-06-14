package com.example.app.module

import com.example.app.di.mainModule
import com.sample.feature.myGson
import com.sample.feature.myLogger
import com.example.app.routing.mainRouting
import com.example.app.routing.postRouting
import com.example.app.routing.userRouting
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin

fun Application.main() {
    install(ContentNegotiation) {
        myGson()
    }

    install(CallLogging) {
        myLogger()
    }

    install(Koin) {
        modules(mainModule)
    }

    install(Locations)



    routing {
        mainRouting()
        postRouting()
        userRouting()
    }

    install(StatusPages)
}



