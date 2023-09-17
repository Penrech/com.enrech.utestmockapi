package com.enrech.routes.content

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.getSingleElement
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.NewGroupDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.LessonGroupRepository
import com.enrech.domain.repository.LessonRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.groupRoutes() {
    val repo by inject<LessonGroupRepository>()
    val lessonRepo by inject<LessonRepository>()

    route("/group") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("lessonId")?.let {
                val data = lessonRepo.getLessonGroup(it)?.let { listOf(it) } ?: emptyList()
                call.respond(data)
            } ?: call.respond(repo.allLessonGroups())
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.getLessonGroup(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                Errors.NotFound("Group with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewGroupDto>() ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.addNewLessonGroup(data.title, data.chapterId)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val title = Json.getSingleElement<String>(call.receiveText(), "title") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("title").toResponse()
            )

            if (repo.editLessonGroup(id, title)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("Group with id: $id").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.removeLessonGroup(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("Group with id: $id").toResponse()
                )
            }
        }
    }
}