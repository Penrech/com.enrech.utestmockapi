package com.enrech.data.request

import kotlinx.serialization.Serializable

@Serializable
data class NewUserDto(val email: String, val password: String)
