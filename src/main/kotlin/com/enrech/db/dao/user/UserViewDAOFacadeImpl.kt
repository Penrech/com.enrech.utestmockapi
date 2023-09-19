package com.enrech.db.dao.user

import com.enrech.db.DatabaseFactory.dbQuery
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.user.*
import java.util.*

class UserViewDAOFacadeImpl : UserViewDAOFacade {
    override suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserViewEntity? = dbQueryWithCatch{
        val lessonUUID = UUID.fromString(lessonId)
        val lesson = Lesson[lessonUUID]
        val group = User[UUID.fromString(userId)].views

        UserView.new {
            this.lesson = lesson
            this.group = group
            completed = lesson.duration == position
            playingPosition = position
            lastUpdate = System.currentTimeMillis()
        }.mapTo()
    }

    override suspend fun getLessonView(id: String): UserViewEntity? = dbQueryWithCatch {
        UserView[UUID.fromString(id)].mapTo()
    }

    override suspend fun editLessonView(id: String, position: Long): Boolean = dbQueryWithCatch {
        val lessonView = UserView[UUID.fromString(id)]
        lessonView.playingPosition = position
        if (lessonView.completed.not()) {
            lessonView.completed = lessonView.lesson.duration == position
        }
        lessonView.flush()
    } ?: false

    override suspend fun deleteLessonView(id: String): Boolean = dbQueryWithCatch {
        UserView[UUID.fromString(id)].delete()
        true
    } ?: false
}