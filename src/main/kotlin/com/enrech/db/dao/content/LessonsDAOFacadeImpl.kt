package com.enrech.db.dao.content

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory.dbQuery
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.content.LessonEntity
import com.enrech.db.model.content.LessonGroup
import com.enrech.db.model.content.Lessons
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class LessonsDAOFacadeImpl : LessonsDAOFacade {
    override suspend fun allLessons(): List<LessonEntity> = dbQueryWithCatch { Lesson.all().mapTo() } ?: emptyList()

    override suspend fun lesson(id: String): LessonEntity? = dbQueryWithCatch { Lesson[UUID.fromString(id)].mapTo() }

    override suspend fun getLessonGroup(lessonId: String): LessonGroup? = dbQueryWithCatch {
        val lesson = Lesson[UUID.fromString(lessonId)]
        lesson.group
    }

    override suspend fun addNewLesson(
        groupId: String,
        name: String,
        description: String,
        duration: Long,
        streamUrl: String
    ): LessonEntity? = dbQueryWithCatch {
        Lesson.new {
            this.groupId = UUID.fromString(groupId)
            this.name = name
            this.description = description
            this.duration = duration
            this.streamUrl = streamUrl
        }.mapTo()
    }

    override suspend fun editLesson(
        id: String,
        name: String?,
        description: String?,
        duration: Long?,
        streamUrl: String?
    ): Boolean = dbQueryWithCatch {
        val lesson = Lesson[UUID.fromString(id)]
        name?.let { lesson.name = it }
        description?.let { lesson.description = it }
        duration?.let { lesson.duration = it }
        streamUrl?.let { lesson.streamUrl = it }
        lesson.flush()
    } ?: false

    override suspend fun deleteLesson(id: String): Boolean = dbQueryWithCatch {
        Lesson[UUID.fromString(id)].delete()
        true
    } ?: false
}