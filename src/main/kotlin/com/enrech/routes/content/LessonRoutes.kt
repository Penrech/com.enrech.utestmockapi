package com.enrech.routes.content

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.EditChapterDto
import com.enrech.data.request.EditLessonDto
import com.enrech.data.request.NewChapterDto
import com.enrech.data.request.NewLessonDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.LessonRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.lessonRoutes() {
    val repo by inject<LessonRepository>()

    route("/lesson") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("groupId")?.let {
                val data = repo.getLessonsByGroup(it)
                call.respond(data)
            } ?: call.respond(repo.allLessons())
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.lesson(id) ?: return@get call.respond(
                status = HttpStatusCode.OK,
                Errors.NotFound("Lesson with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewLessonDto>() ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.addNewLesson(data.groupId, data.name, data.description, data.duration, data.streamUrl)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = call.receiveOrNull<EditLessonDto>()

            if (repo.editLesson(id, data?.name, data?.description, data?.duration, data?.streamUrl)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("Lesson with id: $id").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteLesson(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("Lesson with id: $id").toResponse()
                )
            }
        }
    }
}