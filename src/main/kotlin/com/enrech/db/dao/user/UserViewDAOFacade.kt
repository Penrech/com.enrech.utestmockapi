package com.enrech.db.dao.user

import com.enrech.db.model.user.UserLessonEntity

interface UserViewDAOFacade {
    suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserLessonEntity?
    suspend fun getLessonView(id: String): UserLessonEntity?
    suspend fun editLessonView(id: String, position: Long): Boolean
    suspend fun deleteLessonView(id: String): Boolean
}