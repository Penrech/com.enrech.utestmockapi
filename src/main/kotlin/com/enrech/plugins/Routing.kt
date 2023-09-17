package com.enrech.plugins

import com.enrech.routes.content.content
import com.enrech.routes.content.subjectRoutes
import com.enrech.routes.user.bookmarkRoutes
import com.enrech.routes.user.rewardRoutes
import com.enrech.routes.user.userRoutes
import com.enrech.routes.user.viewRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        content()
        userRoutes()
        bookmarkRoutes()
        rewardRoutes()
        viewRoutes()
    }
}
