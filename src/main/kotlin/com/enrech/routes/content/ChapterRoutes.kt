package com.enrech.routes.content

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.EditChapterDto
import com.enrech.data.request.NewChapterDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.ChapterRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chapterRoutes() {
    val repo by inject<ChapterRepository>()

    route("/chapter") {
        get {
            call.respond(repo.allChapters())
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.chapter(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                Errors.NotFound("Chapter with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewChapterDto>() ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.addNewChapter(data.title, data.order, data.subjectId)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = call.receiveOrNull<EditChapterDto>()

            if (repo.editChapter(id, data?.title, data?.order)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("Chapter").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteChapter(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("Chapter with $id").toResponse()
                )
            }
        }
    }
}