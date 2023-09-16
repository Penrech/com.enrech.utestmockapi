package com.enrech.common.extension

import io.ktor.server.application.*
import io.ktor.server.request.*

suspend inline fun <reified T: Any> ApplicationCall.receiveOrNull() = runCatching {
    this.receive<T>()
}.getOrNull()