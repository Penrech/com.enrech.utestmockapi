package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@Serializable
data class UserBadgeEntity(val userId: String, val obtainedTime: Long)

class UserBadge(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserBadgeEntity> {
    companion object: UUIDEntityClass<UserBadge>(UserBadges)
    val user by User backReferencedOn UserBadges.reward
    var obtainedTime by UserBadges.obtainedTime
    var rewardId by UserBadges.reward
    val reward by UserReward referencedOn UserBadges.reward

    override fun mapTo(): UserBadgeEntity =
        UserBadgeEntity(
            userId = user.id.value.toString(),
            obtainedTime = obtainedTime
        )
}

object UserBadges: UUIDTable() {
    val reward = uuid("reward_id").uniqueIndex().references(UserRewards.id, onDelete = ReferenceOption.CASCADE)
    val obtainedTime = long("obtained_time")
}