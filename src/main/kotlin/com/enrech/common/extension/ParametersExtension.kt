package com.enrech.common.extension

import io.ktor.http.*

fun Parameters.getNonEmptyOrNull(key: String) = this[key].takeIf { it.isNullOrBlank().not() }