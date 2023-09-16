package com.enrech.domain.repository

import com.enrech.db.model.user.UserLessonEntity

interface ViewsRepository {
    suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserLessonEntity?
    suspend fun getLessonView(id: String): UserLessonEntity?
    suspend fun editLessonView(id: String, position: Long): Boolean
    suspend fun deleteLessonView(id: String): Boolean
}