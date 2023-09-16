package com.enrech.domain.repository

import com.enrech.db.model.user.UserEntity

interface UserRepository {
    suspend fun addNewUser(email: String, password: String): UserEntity?
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getUserById(id: String): UserEntity?
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?
    suspend fun editEmail(id: String, email: String): Boolean
    suspend fun editPassword(id: String, password: String): Boolean
    suspend fun deleteUser(id: String): Boolean
}