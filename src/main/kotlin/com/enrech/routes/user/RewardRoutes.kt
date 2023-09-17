package com.enrech.routes.user

import com.enrech.common.extension.getNonEmptyOrNull
import com.enrech.common.extension.receiveOrNull
import com.enrech.data.request.NewBadgeDto
import com.enrech.domain.model.Errors
import com.enrech.domain.repository.RewardsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.rewardRoutes() {
    val repo by inject<RewardsRepository>()

    route("/rewards") {
        get {
            call.request.queryParameters.getNonEmptyOrNull("userId")?.let {
                repo.userRewards(it)?.let {
                    call.respond(it)
                } ?: call.respond(
                    HttpStatusCode.NotFound,
                    Errors.NotFound("Rewards for user with id $it")
                )
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                Errors.MissingInputParameter("userId").toResponse()
            )
        }
        route("/badge") {
            get() {
                call.request.queryParameters.getNonEmptyOrNull("userId")?.let {
                    call.respond(repo.allUserBadges(it))
                } ?: call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.MissingInputParameter("userId").toResponse()
                )
            }
            get("/{id}") {
                val parameters = call.parameters
                val id = parameters.getNonEmptyOrNull("id") ?: return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.MissingInputParameter("id").toResponse()
                )

                repo.badge(id)?.let {
                    call.respond(HttpStatusCode.OK, it)
                } ?: call.respond(
                    HttpStatusCode.NotFound,
                    Errors.NotFound("Badge with id: $id")
                )
            }
            post("/new") {
                val data = call.receiveOrNull<NewBadgeDto>() ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    Errors.BadRequestBody.toResponse()
                )

                repo.addNewBadge(data.userId, data.timestamp)?.let {
                    call.respond(HttpStatusCode.OK, it)
                } ?: call.respond(HttpStatusCode.BadRequest, Errors.Unknown.toResponse())
            }
            post("/delete/{id}") {
                val parameters = call.parameters
                val id = parameters.getNonEmptyOrNull("id") ?: return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    Errors.MissingInputParameter("id").toResponse()
                )

                if (repo.deleteBadge(id)) {
                    call.respond(HttpStatusCode.OK, "")
                } else {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        Errors.DeleteError("Badge with id: $id").toResponse()
                    )
                }
            }
        }
    }
}