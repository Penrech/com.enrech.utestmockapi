package com.enrech.db.model.user

import com.enrech.common.mapTo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@Serializable
data class UserViewSubjectEntity(
    val subjectId: String,
    val chapterViewed: List<UserViewChapterEntity>,
    val totalChapters: Long,
    val chapterCompleted: Long,
    val totalLesson: Long,
    val lessonCompleted: Long,
    val completed: Boolean,
    val currentChapter: String?,
    val currentLesson: String?
)

@Serializable
data class UserViewChapterEntity(
    val chapterId: String,
    val completed: Boolean,
    val lessonCompleted: Long,
    val totalLessons: Long,
    val lessonViewed: List<UserViewEntity>,
    val lastUpdate: Long?,
    val activeLesson: String?,
    val active: Boolean
)

class UserViewGroup(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<UserViewGroup>(UserViewsGroup)

    val user by User backReferencedOn Users.id

    private val rawViews by UserView referrersOn UserViews.group
    val views by UserViewsGroup.views

    val subjectViews get() = rawViews.groupBy { it.subject }.map {
        val subject = it.key
        val subjectViews = it.value

        val chapters = subjectViews.groupBy { it.chapter }.map {
            val chapter = it.key
            val chapterViews = it.value

            val activeLesson = chapterViews.sortedByDescending { it.lastUpdate }.firstOrNull { it.active }?.mapTo()
            val lessonCompleted = chapterViews.count { it.completed }.toLong()
            val totalLessons = chapter.totalLessons

            UserViewChapterEntity(
                chapterId = chapter.id.value.toString(),
                completed = lessonCompleted == totalLessons,
                lessonCompleted = lessonCompleted,
                totalLessons = totalLessons,
                lessonViewed = chapterViews.mapTo(),
                lastUpdate = chapterViews.maxOf { it.lastUpdate },
                activeLesson = activeLesson?.lessonId,
                active = activeLesson != null
            )
        }

        val totalChapters = subject.totalChapters
        val chaptersCompleted = chapters.count { it.completed }.toLong()
        val currentChapter = chapters.sortedByDescending { it.lastUpdate }.firstOrNull { it.active }

        UserViewSubjectEntity(
            subjectId = subject.id.value.toString(),
            chapterViewed = chapters,
            totalChapters = subject.totalChapters,
            chapterCompleted = chaptersCompleted,
            totalLesson = subject.totalLessons,
            lessonCompleted = chapters.sumOf { it.lessonCompleted },
            completed = totalChapters == chaptersCompleted,
            currentChapter = currentChapter?.chapterId,
            currentLesson = currentChapter?.activeLesson
        )
    }
}

object UserViewsGroup: UUIDTable() {
    val views = reference("views", UserViews, onDelete = ReferenceOption.CASCADE)
}