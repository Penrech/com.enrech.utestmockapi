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
    val views: List<UserSubjectEntity>,
    val bookmarks: List<UserBookmarkEntity>,
    val rewards: UserRewardEntity
)

class User(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<UserEntity> {
    companion object : UUIDEntityClass<User>(Users)

    var email by Users.email
    var password by Users.password
    val views by UserSubject referrersOn Users.views
    val bookmarks by UserBookmark referrersOn Users.bookmarks
    val rewards by UserReward referencedOn Users.rewards

    override fun mapTo(): UserEntity =
        UserEntity(
            id = this.id.value.toString(),
            email = email,
            views = views.mapTo(),
            bookmarks = bookmarks.mapTo(),
            rewards = rewards.mapTo()
        )
}

object Users : UUIDTable() {
    val email = varchar("email", 128)
    val password = varchar("password", 512)
    val views = reference("views", UserSubjects)
    val bookmarks = reference("bookmarks", UserBookmarks)
    val rewards = reference("rewards", UserRewards)
}