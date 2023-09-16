package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class EditChapterDto(val title: String? = null, val order: Int? = null)
