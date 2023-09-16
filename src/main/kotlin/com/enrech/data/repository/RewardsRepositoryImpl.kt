package com.enrech.data.repository

import com.enrech.db.dao.user.UserRewardsDAOFacade
import com.enrech.db.model.user.UserBadgeEntity
import com.enrech.db.model.user.UserRewardEntity
import com.enrech.domain.repository.RewardsRepository

class RewardsRepositoryImpl(private val dao: UserRewardsDAOFacade) : RewardsRepository {
    override suspend fun allUserBadges(userId: String): List<UserBadgeEntity> = dao.allUserBadges(userId)

    override suspend fun badge(id: String): UserBadgeEntity? = dao.badge(id)

    override suspend fun userRewards(userId: String): UserRewardEntity? = dao.userRewards(userId)

    override suspend fun addNewBadge(userId: String, timestamp: Long): UserBadgeEntity? = dao.addNewBadge(userId, timestamp)

    override suspend fun deleteBadge(id: String): Boolean = dao.deleteBadge(id)
}