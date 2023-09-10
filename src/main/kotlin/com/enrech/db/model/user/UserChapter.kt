package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.db.model.content.Chapter
import com.enrech.db.model.content.Chapters
import com.enrech.db.model.user.UserLesson.Companion.referencedOn
import com.enrech.db.model.user.UserLesson.Companion.referrersOn
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

@Serializable
data class UserChapterEntity(
    val chapterId: String,
    val completed: Boolean,
    val lessonCompleted: Int,
    val totalLessons: Long,
    val currentLessonId: String?,
    val subjectId: String,
    val lastUpdate: Long
)

class UserChapter(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<UserChapterEntity> {
    companion object : UUIDEntityClass<UserChapter>(UserChapters)

    val lessons by UserLesson referrersOn UserChapters.lessons
    var subject by UserSubject referencedOn UserChapters.subject
    var chapter by Chapter referencedOn UserChapters.chapter
    val completed get() = lessons.all { it.completed }
    val lessonCompleted get() = lessons.count { it.completed }
    val totalLessons get() = lessons.count()
    val lastUpdate get() = lessons.maxOf { it.lastUpdate }
    val activeLesson get() = lessons.sortedByDescending { it.lastUpdate }.firstOrNull { it.active }
    val active get() = activeLesson != null

    override fun mapTo(): UserChapterEntity =
        UserChapterEntity(
            chapterId = chapter.id.value.toString(),
            completed = completed,
            lessonCompleted = lessonCompleted,
            totalLessons = totalLessons,
            currentLessonId = activeLesson?.lesson?.id?.value?.toString(),
            subjectId = subject.id.value.toString(),
            lastUpdate = lastUpdate
        )
}

object UserChapters : UUIDTable() {
    val lessons = reference("lessons", UserLessons)
    val chapter = reference("chapter", Chapters)
    val subject = reference("subject", UserSubjects)

    init {
        index(true, subject, chapter)
    }
}