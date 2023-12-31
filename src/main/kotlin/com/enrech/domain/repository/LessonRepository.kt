package com.enrech.domain.repository

import com.enrech.db.model.content.LessonEntity
import com.enrech.db.model.content.LessonGroup
import com.enrech.db.model.content.LessonGroupEntity

interface LessonRepository {
    suspend fun allLessons(): List<LessonEntity>
    suspend fun getLessonsByGroup(id: String): List<LessonEntity>
    suspend fun lesson(id: String): LessonEntity?
    suspend fun getLessonGroup(lessonId: String): LessonGroupEntity?
    suspend fun addNewLesson(groupId: String, name: String, description: String, duration: Long, streamUrl: String): LessonEntity?
    suspend fun editLesson(id: String, name: String? = null, description: String? = null, duration: Long? = null, streamUrl: String? = null): Boolean
    suspend fun deleteLesson(id: String): Boolean
}