package com.enrech.data.repository

import com.enrech.db.dao.content.LessonGroupDAOFacade
import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.LessonGroupEntity
import com.enrech.domain.repository.LessonGroupRepository

class LessonGroupRepositoryImpl(private val dao: LessonGroupDAOFacade) : LessonGroupRepository {
    override suspend fun allLessonGroups(): List<LessonGroupEntity> = dao.allLessonGroups()

    override suspend fun getLessonsByChapter(id: String): List<LessonGroupEntity> = dao.getLessonGroupsByChapter(id)

    override suspend fun getLessonGroup(id: String): LessonGroupEntity? = dao.getLessonGroup(id)

    override suspend fun getChapter(groupId: String): ChapterEntity? = dao.getChapter(groupId)

    override suspend fun addNewLessonGroup(title: String, chapterId: String): LessonGroupEntity? = dao.addNewLessonGroup(title, chapterId)

    override suspend fun editLessonGroup(id: String, title: String): Boolean = dao.editLessonGroup(id, title)

    override suspend fun removeLessonGroup(id: String): Boolean = dao.removeLessonGroup(id)
}