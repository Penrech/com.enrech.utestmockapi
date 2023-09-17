package com.enrech.db.dao.content

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Chapter
import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.Subject
import com.enrech.db.model.content.SubjectEntity
import java.util.*

class ChapterDAOFacadeImpl : ChapterDAOFacade {

    override suspend fun allChapters(): List<ChapterEntity> = dbQueryWithCatch {
        Chapter.all().mapTo()
    } ?: emptyList()

    override suspend fun chapter(id: String): ChapterEntity? = dbQueryWithCatch {
        Chapter[UUID.fromString(id)].mapTo()
    }

    override suspend fun getSubject(chapterId: String): SubjectEntity? = dbQueryWithCatch {
        val chapter = Chapter[UUID.fromString(chapterId)]
        chapter.subject.mapTo()
    }

    override suspend fun addNewChapter(title: String, order: Int, subjectId: String): ChapterEntity? = dbQueryWithCatch {
        Chapter.new {
            this.title = title
            this.order = order
            this.subjectId = UUID.fromString(subjectId)
        }.mapTo()
    }

    override suspend fun editChapter(id: String, title: String?, order: Int?): Boolean = dbQueryWithCatch {
        val chapter = Chapter[UUID.fromString(id)]
        title?.let { chapter.title = title }
        order?.let { chapter.order = order }
        chapter.flush()
    } ?: false

    override suspend fun deleteChapter(id: String): Boolean = dbQueryWithCatch {
        Chapter[UUID.fromString(id)].delete()
        true
    } ?: false
}