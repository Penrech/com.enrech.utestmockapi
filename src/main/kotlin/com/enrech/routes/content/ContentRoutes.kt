package com.enrech.routes.content

import io.ktor.server.routing.*

fun Route.content() {
    route("/content") {
        subjectRoutes()
        chapterRoutes()
    }
}