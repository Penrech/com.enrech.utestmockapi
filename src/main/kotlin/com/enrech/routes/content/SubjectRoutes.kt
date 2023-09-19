package com.enrech.routes.content

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.getSingleElement
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.NewSubjectDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.ChapterRepository
import com.enrech.domain.repository.SubjectRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.subjectRoutes() {
    val repo by inject<SubjectRepository>()
    val chapterRepo by inject<ChapterRepository>()

    route("/subject") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("chapterId")?.let {
                val data = chapterRepo.getSubject(it)?.let { listOf(it) } ?: emptyList()
                call.respond(data)
            } ?: call.respond(repo.allSubjects())
        }
        get("/{id}") {
            val id = call.parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            val data = repo.subject(id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                Errors.NotFound("Subject with id: $id").toResponse()
            )

            call.respond(data)
        }
        post("/new") {
            val data = call.receiveOrNull<NewSubjectDto>() ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.BadRequestBody.toResponse()
            )

            repo.addNewSubject(data.title, data.acronym)?.let {
                call.respond(HttpStatusCode.Created, it)
            } ?: call.respond(status = HttpStatusCode.fromValue(400), Errors.Unknown.toResponse())
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

            if (repo.editSubject(id, title)) {
                call.respond(status = HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.UpdateError("Subject").toResponse()
                )
            }
        }
        post("/delete/{id}") {
            val parameters = call.parameters
            val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("id").toResponse()
            )

            if (repo.deleteSubject(id)) {
                call.respond(HttpStatusCode.OK, "")
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.DeleteError("Subject with $id").toResponse()
                )
            }
        }
    }
}