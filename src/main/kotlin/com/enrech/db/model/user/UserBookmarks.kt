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
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@Serializable
data class UserBookmarkEntity(val id: String, val lessonId: String, val position: Long, val content: String)

class UserBookmark(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserBookmarkEntity> {
    companion object: UUIDEntityClass<UserBookmark>(UserBookmarks)

    var lessonId by UserBookmarks.lesson
    var userId by UserBookmarks.user
    val lesson by Lesson referencedOn UserBookmarks.lesson
    val user by User referencedOn UserBookmarks.user
    var bookmarkPosition by UserBookmarks.bookmarkPosition
    var description by UserBookmarks.description

    override fun mapTo(): UserBookmarkEntity =
        UserBookmarkEntity(
            id = id.value.toString(),
            lessonId = lessonId.toString(),
            position = bookmarkPosition,
            content = description
        )
}

object UserBookmarks : UUIDTable() {
    val user = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val lesson = uuid("lesson_id").references(Lessons.id, onDelete = ReferenceOption.CASCADE)
    val bookmarkPosition = long("position")
    val description = text("description")
}