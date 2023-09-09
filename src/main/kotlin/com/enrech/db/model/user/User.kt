package com.enrech.db.model.user

import org.jetbrains.exposed.dao.id.UUIDTable
import java.io.Serializable

object User: UUIDTable() {
    val email = varchar("email",128)
    val password = varchar("password", 512)
}

data class UserEntity(val id: String, val email: String): Serializable