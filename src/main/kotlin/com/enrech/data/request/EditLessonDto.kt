package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class EditLessonDto(
    val name: String? = null,
    val description: String? = null,
    val duration: Long? = null,
    val streamUrl: String? = null
)
