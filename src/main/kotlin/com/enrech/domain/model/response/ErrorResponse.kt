package com.enrech.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val type: String, val message: String)
