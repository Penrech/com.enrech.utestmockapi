package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.content.Lessons
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
data class UserLessonEntity(
    val lessonId: String,
    val completed: Boolean,
    val playingPosition: Long,
    val chapterId: String,
    val lastUpdate: Long
)

class UserLesson(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<UserLessonEntity> {
    companion object : UUIDEntityClass<UserLesson>(UserLessons)

    var lesson by Lesson referencedOn UserLessons.lesson
    var completed by UserLessons.completed
    var chapter by UserChapter referencedOn UserLessons.chapter
    var playingPosition by UserLessons.playingPosition
    var lastUpdate by UserLessons.lastUpdate
    val active
        get() =
            (lesson.duration > 0)
                .and(!completed)
                .and(playingPosition > 0)

    override fun mapTo(): UserLessonEntity =
        UserLessonEntity(
            lessonId = lesson.id.value.toString(),
            completed = completed,
            playingPosition = playingPosition,
            chapterId = chapter.id.value.toString(),
            lastUpdate = lastUpdate
        )
}

object UserLessons : UUIDTable() {
    val lesson = reference("lesson", Lessons)
    val chapter = reference("chapter", UserChapters)
    val completed = bool("completed")
    val playingPosition = long("position")
    val lastUpdate = long("last_update")

    init {
        index(true, lesson)
    }
}