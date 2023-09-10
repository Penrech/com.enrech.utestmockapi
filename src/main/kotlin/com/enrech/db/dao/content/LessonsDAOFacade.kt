package com.enrech.db.dao.content

import com.enrech.db.model.content.LessonEntity
import com.enrech.db.model.content.LessonGroup

interface LessonsDAOFacade {
    suspend fun allLessons(): List<LessonEntity>
    suspend fun lesson(id: String): LessonEntity?
    suspend fun getLessonGroup(lessonId: String): LessonGroup?
    suspend fun addNewLesson(groupId: String, name: String, description: String, duration: Long, streamUrl: String): LessonEntity?
    suspend fun editLesson(id: String, name: String? = null, description: String? = null, duration: Long? = null, streamUrl: String? = null): Boolean
    suspend fun deleteLesson(id: String): Boolean
}