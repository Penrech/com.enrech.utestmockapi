package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewGroupDto(val title: String, val chapterId: String)
