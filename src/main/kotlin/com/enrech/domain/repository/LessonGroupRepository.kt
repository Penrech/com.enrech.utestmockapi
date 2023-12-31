package com.enrech.domain.repository

import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.LessonGroupEntity

interface LessonGroupRepository {
    suspend fun allLessonGroups(): List<LessonGroupEntity>
    suspend fun getLessonsByChapter(id: String): List<LessonGroupEntity>
    suspend fun getLessonGroup(id: String): LessonGroupEntity?
    suspend fun getChapter(groupId: String): ChapterEntity?
    suspend fun addNewLessonGroup(title: String, chapterId: String): LessonGroupEntity?
    suspend fun editLessonGroup(id: String, title: String): Boolean
    suspend fun removeLessonGroup(id: String): Boolean
}