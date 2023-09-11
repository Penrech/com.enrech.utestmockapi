package com.enrech.plugins

import com.enrech.db.dao.di.contentDaoModule
import com.enrech.db.dao.di.userDaoModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            contentDaoModule,
            userDaoModule
        )
    }
}