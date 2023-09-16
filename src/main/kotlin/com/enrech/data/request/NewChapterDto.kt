package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewChapterDto(val title: String, val order: Int, val subjectId: String)