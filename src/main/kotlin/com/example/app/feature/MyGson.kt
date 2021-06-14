package com.sample.feature

import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson

fun ContentNegotiation.Configuration.myGson() {
    gson {
        setPrettyPrinting()
        disableHtmlEscaping()
    }
}