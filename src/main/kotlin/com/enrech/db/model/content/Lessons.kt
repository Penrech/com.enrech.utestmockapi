package com.enrech.db.model.content

import com.enrech.common.BaseMapper
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@kotlinx.serialization.Serializable
data class LessonEntity(val id: String, val name: String, val description: String, val duration: Long, val streamUrl: String)

class Lesson(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<LessonEntity> {
    companion object : UUIDEntityClass<Lesson>(Lessons)
    var name by Lessons.name
    var description by Lessons.description
    var duration by Lessons.duration
    var streamUrl by Lessons.streamUrl
    var groupId by Lessons.group
    val group by LessonGroup referencedOn Lessons.group

    override fun mapTo(): LessonEntity = LessonEntity(this.id.value.toString(), name, description, duration, streamUrl)
}

object Lessons: UUIDTable() {
    val group = uuid("group_id").uniqueIndex().references(LessonGroups.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 1024)
    val description = varchar("description", 1024)
    val duration = long("duration")
    val streamUrl = varchar("stream_url", 1024)
}