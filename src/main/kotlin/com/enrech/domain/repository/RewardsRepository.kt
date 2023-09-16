package com.enrech.domain.repository

import com.enrech.db.model.user.UserBadgeEntity
import com.enrech.db.model.user.UserRewardEntity

interface RewardsRepository {
    suspend fun allUserBadges(userId: String): List<UserBadgeEntity>
    suspend fun badge(id: String): UserBadgeEntity?
    suspend fun userRewards(userId: String): UserRewardEntity?
    suspend fun addNewBadge(userId: String, timestamp: Long): UserBadgeEntity?
    suspend fun deleteBadge(id: String): Boolean
}