package com.enrech.db.dao.user

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.user.*
import java.util.UUID

class UserRewardsDAOFacadeImpl : UserRewardsDAOFacade {
    override suspend fun allUserBadges(userId: String): List<UserBadgeEntity> = dbQueryWithCatch {
        User[UUID.fromString(userId)].rewards.badges.mapTo()
    } ?: emptyList()

    override suspend fun badge(id: String): UserBadgeEntity? = dbQueryWithCatch {
        UserBadge[UUID.fromString(id)].mapTo()
    }

    override suspend fun userRewards(userId: String): UserRewardEntity? = dbQueryWithCatch {
        User[UUID.fromString(userId)].rewards.mapTo()
    }

    override suspend fun addNewBadge(userId: String, timestamp: Long): UserBadgeEntity? = dbQueryWithCatch {
        val userReward = User[UUID.fromString(userId)].rewards
        UserBadge.new {
            this.rewardId = userReward.id.value
            this.obtainedTime = timestamp
        }.mapTo()
    }

    override suspend fun deleteBadge(id: String): Boolean = dbQueryWithCatch {
        UserBadge[UUID.fromString(id)].delete()
        true
    } ?: false
}