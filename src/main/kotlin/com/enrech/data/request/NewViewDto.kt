package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewViewDto(val lessonId: String, val userId: String, val position: Long)
