package com.enrech.plugins

import com.enrech.db.DatabaseFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    DatabaseFactory.init(environment.config)
}
