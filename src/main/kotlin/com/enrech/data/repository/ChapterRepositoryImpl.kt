package com.enrech.data.repository

import com.enrech.db.dao.content.ChapterDAOFacade
import com.enrech.db.dao.content.ChapterDAOFacadeImpl
import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.SubjectEntity
import com.enrech.domain.repository.ChapterRepository

class ChapterRepositoryImpl(private val dao: ChapterDAOFacade) : ChapterRepository {
    override suspend fun allChapters(): List<ChapterEntity> = dao.allChapters()

    override suspend fun chapter(id: String): ChapterEntity? = dao.chapter(id)

    override suspend fun getSubject(chapterId: String): SubjectEntity? = dao.getSubject(chapterId)

    override suspend fun addNewChapter(title: String, order: Int, subjectId: String): ChapterEntity? =
        dao.addNewChapter(title, order, subjectId)

    override suspend fun editChapter(id: String, title: String?, order: Int?): Boolean =
        dao.editChapter(id, title, order)

    override suspend fun deleteChapter(id: String): Boolean = dao.deleteChapter(id)
}