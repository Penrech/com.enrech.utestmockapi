package com.enrech.routes.user

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.getSingleElement
import com.enrech.data.request.NewUserDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

//todo: remove this as this is just for testing purposes
fun Route.userRoutes() {
    val repo by inject<UserRepository>()

    route("/user") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("email")?.let { email ->
                repo.getUserByEmail(email)?.let {
                    call.respond(HttpStatusCode.OK, it)
                } ?: call.respond(
                    HttpStatusCode.NotFound,
                    Errors.NotFound("User with email: $email").toResponse()
                )
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("email").toResponse()
            )
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.getUserById(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                Errors.NotFound("User with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receive<NewUserDto>()

            repo.addNewUser(data.email, data.password)?.let {
                call.respond(HttpStatusCode.Created, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}/email") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val email = Json.getSingleElement<String>(call.receiveText(), "email") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("email").toResponse()
            )

            if (repo.editEmail(id, email)) {
                call.respond(
                    HttpStatusCode.OK,
                    ""
                )
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("User email with id: $id and new email: $email").toResponse()
                )
            }
        }
        patch("/edit/{id}/password") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val password = Json.getSingleElement<String>(call.receiveText(), "password") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("password").toResponse()
            )

            if (repo.editPassword(id, password)) {
                call.respond(
                    HttpStatusCode.OK,
                    ""
                )
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("User password with id: $id and new password: $password").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteUser(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.DeleteError("User with id: $id").toResponse()
                )
            }
        }
    }
}