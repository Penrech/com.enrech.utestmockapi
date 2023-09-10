package com.enrech.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenEntity(val accessToken: String, val refreshToken: String)
