package com.enrech.db.dao.user

import com.enrech.db.model.user.UserBadgeEntity
import com.enrech.db.model.user.UserRewardEntity

interface UserRewardsDAOFacade {
    suspend fun allUserBadges(userId: String): List<UserBadgeEntity>
    suspend fun badge(id: String): UserBadgeEntity?
    suspend fun userRewards(userId: String): UserRewardEntity?
    suspend fun addNewBadge(userId: String, timestamp: Long): UserBadgeEntity?
    suspend fun deleteBadge(id: String): Boolean
}