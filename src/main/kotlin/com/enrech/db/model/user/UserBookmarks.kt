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
data class UserBookmarkEntity(val lessonId: String, val position: Long, val content: String)

class UserBookmark(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserBookmarkEntity> {
    companion object: UUIDEntityClass<UserBookmark>(UserBookmarks)
    val lesson by Lesson referencedOn UserBookmarks.lesson
    val lessonId get() = lesson.id.value.toString()
    val bookmarkPosition by UserBookmarks.bookmarkPosition
    val description by UserBookmarks.description

    override fun mapTo(): UserBookmarkEntity =
        UserBookmarkEntity(
            lessonId = lessonId,
            position = bookmarkPosition,
            content = description
        )
}

object UserBookmarks : UUIDTable() {
    val user = reference("user", Users)
    val lesson = reference("lesson", Lessons)
    val bookmarkPosition = long("position")
    val description = text("description")

    init {
        index(true, user, lesson)
    }
}