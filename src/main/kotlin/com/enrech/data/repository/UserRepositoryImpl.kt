package com.enrech.data.repository

import com.enrech.db.dao.user.UserDAOFacade
import com.enrech.db.model.user.UserEntity
import com.enrech.domain.repository.UserRepository

class UserRepositoryImpl(private val dao: UserDAOFacade) : UserRepository {
    override suspend fun addNewUser(email: String, password: String): UserEntity? = dao.addNewUser(email, password)

    override suspend fun getUserByEmail(email: String): UserEntity? = dao.getUserByEmail(email)

    override suspend fun getUserById(id: String): UserEntity? = dao.getUserById(id)

    override suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? = dao.getUserByEmailAndPassword(email, password)

    override suspend fun editEmail(id: String, email: String): Boolean = dao.editEmail(id, email)

    override suspend fun editPassword(id: String, password: String): Boolean = dao.editPassword(id, password)

    override suspend fun deleteUser(id: String): Boolean = dao.deleteUser(id)
}