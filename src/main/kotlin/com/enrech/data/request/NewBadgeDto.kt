package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewBadgeDto(val userId: String, val timestamp: Long)
