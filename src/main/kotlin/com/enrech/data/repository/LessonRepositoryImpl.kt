package com.enrech.data.repository

import com.enrech.db.dao.content.LessonsDAOFacade
import com.enrech.db.model.content.LessonEntity
import com.enrech.db.model.content.LessonGroup
import com.enrech.domain.repository.LessonRepository

class LessonRepositoryImpl(private val dao: LessonsDAOFacade) : LessonRepository {
    override suspend fun allLessons(): List<LessonEntity> = dao.allLessons()

    override suspend fun lesson(id: String): LessonEntity? = dao.lesson(id)

    override suspend fun getLessonGroup(lessonId: String): LessonGroup? = dao.getLessonGroup(lessonId)

    override suspend fun addNewLesson(
        groupId: String,
        name: String,
        description: String,
        duration: Long,
        streamUrl: String
    ): LessonEntity? = dao.addNewLesson(groupId, name, description, duration, streamUrl)

    override suspend fun editLesson(
        id: String,
        name: String?,
        description: String?,
        duration: Long?,
        streamUrl: String?
    ): Boolean = dao.editLesson(id, name, description, duration, streamUrl)

    override suspend fun deleteLesson(id: String): Boolean = dao.deleteLesson(id)
}