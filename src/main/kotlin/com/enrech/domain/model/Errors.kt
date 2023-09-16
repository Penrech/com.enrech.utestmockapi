package com.enrech.domain.model

import com.enrech.domain.model.response.ErrorResponse

sealed class Errors {
    fun toResponse() = ErrorResponse(type, message)
    protected abstract val type: String
    protected abstract val message: String

    data class MissingInputParameter(val field: String): Errors() {
        override val type: String = "Missing_Input_Parameter"
        override val message: String = "Missing $field input parameter"
    }

    data object BadRequestBody: Errors() {
        override val type: String = "Bad_Request_Body"
        override val message: String = "Wrong body parameters"
    }

    data class NotFound(val element: String): Errors() {
        override val type: String = "Not_Found"
        override val message: String = "No element $element found"
    }

    data object Unknown: Errors() {
        override val type: String = "Unknown"
        override val message: String = "Unknown Error"
    }

    data class UpdateError(val field: String): Errors() {
        override val type: String = "Not_Updated"
        override val message: String = "Error while updating $field"
    }

    data class DeleteError(val element: String): Errors() {
        override val type: String = "Not_Deleted"
        override val message: String = "Error while removing $element"
    }
}