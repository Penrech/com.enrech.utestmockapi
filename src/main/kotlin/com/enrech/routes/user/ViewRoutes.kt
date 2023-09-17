package com.enrech.routes.user

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.getSingleElement
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.NewViewDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.ViewsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.viewRoutes() {
    val repo by inject<ViewsRepository>()

    route("/views") {
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.getLessonView(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                Errors.NotFound("View with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewViewDto>() ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.logNewLessonView(data.lessonId, data.userId, data.position)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val position = Json.getSingleElement<Long>(call.receiveText(), "position") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("position").toResponse()
            )

            if (repo.editLessonView(id, position)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("LoggedView with id: $id").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteLessonView(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("LoggedView with id: $id").toResponse()
                )
            }
        }
    }
}