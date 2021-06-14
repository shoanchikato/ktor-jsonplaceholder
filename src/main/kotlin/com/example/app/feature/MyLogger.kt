package com.sample.feature

import io.ktor.features.*
import org.slf4j.event.Level

fun CallLogging.Configuration.myLogger() {
    level = Level.INFO
}
