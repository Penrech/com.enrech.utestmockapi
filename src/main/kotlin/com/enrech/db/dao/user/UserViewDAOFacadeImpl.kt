package com.enrech.db.dao.user

import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.user.*
import java.util.*

class UserViewDAOFacadeImpl : UserViewDAOFacade {
    override suspend fun logNewLessonView(lessonId: String, userId: String, position: Long): UserLessonEntity? = dbQueryWithCatch {
        val lesson = Lesson[UUID.fromString(lessonId)]
        val userChapter = UserChapter.find { UserChapters.chapter eq lesson.group.chapter.id }.firstOrNull()

        val actualOrNewUserChapter = userChapter ?: run {
            val userSubject = UserSubject.find { UserSubjects.subject eq lesson.group.chapter.subject.id }.firstOrNull()

            userSubject?.let {
                UserChapter.new {
                    this.chapter = lesson.group.chapter
                    this.subject = userSubject
                }
            } ?: run {
                val newUserSubject = UserSubject.new {
                    subject = lesson.group.chapter.subject
                    user = User[UUID.fromString(userId)]
                }
                UserChapter.new {
                    this.chapter = lesson.group.chapter
                    this.subject = newUserSubject
                }
            }
        }

        UserLesson.new {
            this.lesson = lesson
            this.chapter = actualOrNewUserChapter
            this.completed = lesson.duration == position
            this.playingPosition = position
            this.lastUpdate = System.currentTimeMillis()
        }.mapTo()
    }

    override suspend fun getLessonView(id: String): UserLessonEntity? = dbQueryWithCatch {
        UserLesson[UUID.fromString(id)].mapTo()
    }

    override suspend fun editLessonView(id: String, position: Long): Boolean = dbQueryWithCatch {
        val lessonView = UserLesson[UUID.fromString(id)]
        lessonView.playingPosition = position
        if (lessonView.completed.not()) {
            lessonView.completed = lessonView.lesson.duration == position
        }
        lessonView.flush()
    } ?: false

    override suspend fun deleteLessonView(id: String): Boolean = dbQueryWithCatch {
        UserLesson[UUID.fromString(id)].delete()
        true
    } ?: false
}