package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewSubjectDto(val title: String, val acronym: String)
