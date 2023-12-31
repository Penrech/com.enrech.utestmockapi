package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.content.Lessons
import com.enrech.db.model.content.Subject.Companion.backReferencedOn
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table.Dual.references
import java.util.*

@Serializable
data class UserViewEntity(
    val id: String,
    val lessonId: String,
    val completed: Boolean,
    val playingPosition: Long,
    val lastUpdate: Long,
    val active: Boolean
)

class UserView(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<UserViewEntity> {
    companion object: UUIDEntityClass<UserView>(UserViews)
    var lesson by Lesson referencedOn UserViews.lesson
    var completed by UserViews.completed
    var playingPosition by UserViews.playingPosition
    var lastUpdate by UserViews.lastUpdate
    var group by UserViewGroup referencedOn UserViews.group

    val active
        get() =
            (lesson.duration > 0)
                .and(!completed)
                .and(playingPosition > 0)

    val chapter get() = lesson.group.chapter
    val subject get() = chapter.subject

    override fun mapTo(): UserViewEntity =
        UserViewEntity(
            id = id.value.toString(),
            lessonId = lesson.id.value.toString(),
            completed = completed,
            playingPosition = playingPosition,
            lastUpdate = lastUpdate,
            active = active
        )
}

object UserViews: UUIDTable() {
    val lesson = reference("lesson", Lessons, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    val group = reference("group", UserViewsGroup, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val completed = bool("completed")
    val playingPosition = long("position")
    val lastUpdate = long("last_update")
}