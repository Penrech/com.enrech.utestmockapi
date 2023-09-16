package com.enrech.routes.user

import com.enrech.data.request.NewUserDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val repo by inject<UserRepository>()

    route("/user") {
        post("/new") {
            val data = call.receive<NewUserDto>()

            repo.addNewUser(data.email, data.password)?.let {
                call.respond(HttpStatusCode.Created, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
    }
}