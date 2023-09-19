package com.enrech.db.dao.user

import com.enrech.db.model.user.UserViewEntity

interface UserViewDAOFacade {
    suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserViewEntity?
    suspend fun getLessonView(id: String): UserViewEntity?
    suspend fun editLessonView(id: String, position: Long): Boolean
    suspend fun deleteLessonView(id: String): Boolean
}