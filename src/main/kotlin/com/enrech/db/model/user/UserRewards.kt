package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import com.enrech.db.model.user.UserReward.Companion.optionalReferrersOn
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@Serializable
data class UserRewardEntity(val userId: String, val badges: List<UserBadgeEntity>, val lastObtainedTime: Long?)

class UserReward(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserRewardEntity> {
    companion object: UUIDEntityClass<UserReward>(UserRewards)

    var user by User referencedOn UserRewards.user
    val badges by UserBadge referrersOn UserBadges.reward

    override fun mapTo(): UserRewardEntity =
        UserRewardEntity(
            userId = user.id.value.toString(),
            badges = badges.mapTo(),
            lastObtainedTime = badges.maxOfOrNull { it.obtainedTime }
        )
}

object UserRewards : UUIDTable() {
    val user = reference("user", Users)
}