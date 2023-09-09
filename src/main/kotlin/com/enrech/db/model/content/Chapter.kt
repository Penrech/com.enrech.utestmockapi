package com.enrech.db.model.content

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import com.enrech.db.model.content.LessonGroup.Companion.referrersOn
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.io.Serializable
import java.util.UUID

data class ChapterEntity(val id: String, val title: String, val order: Int, val lessonGroups: List<LessonGroupEntity>, val totalLessons: Long): Serializable

class Chapter(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<ChapterEntity> {
    companion object: UUIDEntityClass<Chapter>(Chapters)
    var title by Chapters.title
    var order by Chapters.order
    val lessonGroups by LessonGroup referrersOn LessonGroups.id
    val totalLessons get() = lessonGroups.sumOf { it.lessonsQuantity }

    override fun mapTo(): ChapterEntity =
        ChapterEntity(
            id = this.id.value.toString(),
            title = title,
            order = order,
            lessonGroups = lessonGroups.mapTo(),
            totalLessons = totalLessons
        )
}

object Chapters: UUIDTable() {
    val title = varchar("title", 1024)
    val order = integer("order")
}