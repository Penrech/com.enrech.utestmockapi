package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

@Serializable
data class UserEntity(
    val id: String,
    val email: String,
    val bookmarks: List<UserBookmarkEntity>,
    val rewards: UserRewardEntity
)

class User(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<UserEntity> {
    companion object : UUIDEntityClass<User>(Users)

    var email by Users.email
    var password by Users.password
    val bookmarks get() = UserBookmark.find { UserBookmarks.user eq this@User.id.value }

    val rewards by UserReward backReferencedOn UserRewards.user

    override fun mapTo(): UserEntity =
        UserEntity(
            id = this.id.value.toString(),
            email = email,
            bookmarks = bookmarks.mapTo(),
            rewards = rewards.mapTo()
        )
}

object Users : UUIDTable() {
    val email = varchar("email", 128)
    val password = varchar("password", 512)

    init {
        index(true, email)
    }
}