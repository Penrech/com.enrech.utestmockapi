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
data class UserBadgeEntity(val id: String, val userId: String, val obtainedTime: Long)

class UserBadge(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserBadgeEntity> {
    companion object: UUIDEntityClass<UserBadge>(UserBadges)
    var obtainedTime by UserBadges.obtainedTime
    var rewardId by UserBadges.reward
    val reward by UserReward referencedOn UserBadges.reward
    val user get() = reward.user.id

    override fun mapTo(): UserBadgeEntity =
        UserBadgeEntity(
            id = this.id.value.toString(),
            userId = user.toString(),
            obtainedTime = obtainedTime
        )
}

object UserBadges: UUIDTable() {
    val reward = uuid("reward_id").references(UserRewards.id, onDelete = ReferenceOption.CASCADE)
    val obtainedTime = long("obtained_time")
}