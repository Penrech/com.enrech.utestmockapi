package com.enrech.db.dao.content

import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.SubjectEntity

interface ChapterDAOFacade {
    suspend fun allChapters(): List<ChapterEntity>
    suspend fun chapter(id: String): ChapterEntity?
    suspend fun getSubject(chapterId: String): SubjectEntity?
    suspend fun addNewChapter(title: String, order: Int, subjectId: String): ChapterEntity?
    suspend fun editChapter(id: String, title: String? = null, order: Int? = null): Boolean
    suspend fun deleteChapter(id: String): Boolean
}