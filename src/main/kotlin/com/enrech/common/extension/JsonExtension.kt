package com.enrech.common.extension

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
inline fun <reified T: Any> Json.getSingleElement(json: String, key: String): T? = try {
    val jsonObject = Json.decodeFromString<JsonObject>(json)
    jsonObject[key]?.let {
        Json.decodeFromJsonElement(it)
    }
} catch (e: Exception) {
    null
}