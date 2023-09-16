package com.enrech.db.dao.user

import com.enrech.db.DatabaseFactory.dbQuery
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.user.User
import com.enrech.db.model.user.UserEntity
import com.enrech.db.model.user.UserReward
import com.enrech.db.model.user.Users
import org.jetbrains.exposed.sql.and
import java.util.*

class UserDAOFacadeImpl : UserDAOFacade {

    override suspend fun addNewUser(email: String, password: String): UserEntity? = dbQuery {
       User.new {
           val user = this
           this.email = email
           this.password = password
           this.rewards = UserReward.new { this.user = user}
       }.mapTo()
    }

    override suspend fun getUserByEmail(email: String): UserEntity? = dbQueryWithCatch {
        User.find { Users.email eq email }.firstOrNull()?.mapTo()
    }

    override suspend fun getUserById(id: String): UserEntity? = dbQueryWithCatch {
        User[UUID.fromString(id)].mapTo()
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? = dbQueryWithCatch {
        User.find { (Users.email eq email) and (Users.password eq password) }.firstOrNull()?.mapTo()
    }

    override suspend fun editEmail(id: String, email: String): Boolean = dbQueryWithCatch {
        val user = User[UUID.fromString(id)]
        user.email = email
        user.flush()
    } ?: false

    override suspend fun editPassword(id: String, password: String): Boolean = dbQueryWithCatch {
        val user = User[UUID.fromString(id)]
        user.password = password
        user.flush()
    } ?: false

    override suspend fun deleteUser(id: String): Boolean = dbQueryWithCatch {
        User[UUID.fromString(id)].delete()
        true
    } ?: false
}