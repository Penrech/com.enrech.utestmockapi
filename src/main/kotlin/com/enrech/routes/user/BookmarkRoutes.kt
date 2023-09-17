package com.enrech.routes.user

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.getSingleElement
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.NewBookmarkDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.BookmarksRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.bookmarkRoutes() {
    val repo by inject<BookmarksRepository>()

    route("/bookmark") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("userId")?.let {
                val data = repo.allUserBookmarks(it)
                call.respond(data)
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("userId").toResponse()
            )
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.bookmark(id) ?: return@get call.respond(
                HttpStatusCode.NotFound,
                Errors.NotFound("Bookmark with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewBookmarkDto>() ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.addNewBookmark(data.lessonId, data.userId, data.position, data.content)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
        }
        patch("/edit/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val content = Json.getSingleElement<String>(call.receiveText(), "content") ?: return@patch call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("content").toResponse()
            )

            if (repo.editBookmark(id, content)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.UpdateError("Bookmark with id: $id").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteBookmark(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("Bookmark with id: $id").toResponse()
                )
            }
        }
    }
}
