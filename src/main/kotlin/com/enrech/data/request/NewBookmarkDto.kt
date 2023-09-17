package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewBookmarkDto(
    val lessonId: String,
    val userId: String,
    val position: Long,
    val content: String
)
