package com.enrech.data.repository

import com.enrech.db.dao.user.UserViewDAOFacade
import com.enrech.db.model.user.UserLessonEntity
import com.enrech.domain.repository.ViewsRepository

class ViewsRepositoryImpl(private val dao: UserViewDAOFacade) : ViewsRepository {
    override suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserLessonEntity? =
        dao.logNewLessonView(lessonId, userId, position)

    override suspend fun getLessonView(id: String): UserLessonEntity? = dao.getLessonView(id)

    override suspend fun editLessonView(id: String, position: Long): Boolean = dao.editLessonView(id, position)

    override suspend fun deleteLessonView(id: String): Boolean = dao.deleteLessonView(id)
}