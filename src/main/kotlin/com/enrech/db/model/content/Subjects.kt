package com.enrech.db.model.content

import com.enrech.common.BaseMapper
import com.enrech.common.mapTo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

@Serializable
data class SubjectEntity(
    val id: String,
    val name: String,
    val chapters: List<ChapterEntity>,
    val totalChapters: Long,
    val totalLessons: Long
)

class Subject(id: EntityID<UUID>) : UUIDEntity(id), BaseMapper<SubjectEntity> {
    companion object : UUIDEntityClass<Subject>(Subjects)

    var name by Subjects.name
    val chapters by Chapter.referrersOn(Chapters.subject)
    val totalChapters get() = chapters.count()
    val totalLessons get() = chapters.sumOf { it.totalLessons }

    override fun mapTo(): SubjectEntity =
        SubjectEntity(
            id = this.id.value.toString(),
            name = name,
            chapters = chapters.mapTo(),
            totalChapters = totalChapters,
            totalLessons = totalLessons
        )
}

object Subjects : UUIDTable() {
    val name = varchar("name", 512)
}