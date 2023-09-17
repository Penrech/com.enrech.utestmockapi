package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewLessonDto(val name: String, val description: String, val duration: Long, val streamUrl: String, val groupId: String)
