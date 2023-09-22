package com.enrech.db.model.content

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.deleteAll
import java.util.UUID

@Serializable
data class ChapterEntity(val id: String, val subjectId: String, val title: String, val order: Int, val lessonGroups: List<LessonGroupEntity>, val totalLessons: Long)

class Chapter(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<ChapterEntity> {
    companion object: UUIDEntityClass<Chapter>(Chapters)
    var title by Chapters.title
    var order by Chapters.order
    var subject by Subject referencedOn Chapters.subject
    val lessonGroups by LessonGroup referrersOn LessonGroups.chapter
    val totalLessons get() = lessonGroups.sumOf { it.lessonsQuantity }

    override fun mapTo(): ChapterEntity =
        ChapterEntity(
            id = this.id.value.toString(),
            subjectId = subject.id.value.toString(),
            title = title,
            order = order,
            lessonGroups = lessonGroups.mapTo(),
            totalLessons = totalLessons
        )
}

object Chapters: UUIDTable() {
    val title = varchar("title", 1024)
    val order = integer("order")
    val subject = reference("subject", Subjects, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
}