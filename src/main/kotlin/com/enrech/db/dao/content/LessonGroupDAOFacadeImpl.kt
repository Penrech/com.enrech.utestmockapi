package com.enrech.db.dao.content

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Chapter
import com.enrech.db.model.content.ChapterEntity
import com.enrech.db.model.content.LessonGroup
import com.enrech.db.model.content.LessonGroupEntity
import java.util.*

class LessonGroupDAOFacadeImpl : LessonGroupDAOFacade {

    override suspend fun allLessonGroups(): List<LessonGroupEntity> = dbQueryWithCatch {
        LessonGroup.all().mapTo()
    } ?: emptyList()

    override suspend fun getLessonGroup(id: String): LessonGroupEntity? = dbQueryWithCatch {
        LessonGroup[UUID.fromString(id)].mapTo()
    }

    override suspend fun getChapter(groupId: String): ChapterEntity? = dbQueryWithCatch {
        LessonGroup[UUID.fromString(groupId)].chapter.mapTo()
    }

    override suspend fun addNewLessonGroup(title: String, chapterId: String): LessonGroupEntity? = dbQueryWithCatch {
        LessonGroup.new {
            this.title = title
            this.chapterId = UUID.fromString(chapterId)
        }.mapTo()
    }

    override suspend fun editLessonGroup(id: String, title: String): Boolean = dbQueryWithCatch {
        val group = LessonGroup[UUID.fromString(id)]
        group.title = title
        group.flush()
    } ?: false

    override suspend fun removeLessonGroup(id: String): Boolean = dbQueryWithCatch {
        LessonGroup[UUID.fromString(id)].delete()
        true
    } ?: false
}