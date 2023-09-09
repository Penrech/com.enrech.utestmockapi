package com.enrech.db.model.content

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.io.Serializable
import java.util.UUID

data class LessonGroupEntity(val id: String, val title: String, val lessonsQuantity: Long, val lessons: List<LessonEntity>): Serializable

class LessonGroup(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<LessonGroupEntity> {
    companion object: UUIDEntityClass<LessonGroup>(LessonGroups)
    var title by LessonGroups.title
    val lessons by Lesson referrersOn Lessons.id
    val lessonsQuantity get() = lessons.count()

    override fun mapTo(): LessonGroupEntity = LessonGroupEntity(
        id = this.id.value.toString(),
        title = title,
        lessonsQuantity = lessonsQuantity,
        lessons = lessons.mapTo()
    )
}

object LessonGroups: UUIDTable() {
    val title = varchar("title", 1024)
}