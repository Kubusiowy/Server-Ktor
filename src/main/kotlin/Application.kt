package com.firek

import com.firek.database.DatabaseConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.Database

fun main(){
    embeddedServer(Netty, port = 8080){
        module()
    }.start(wait = true)
}

fun Application.module(){
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRouting()
    configureErrorHandling()
    DatabaseConfig.init()
}
