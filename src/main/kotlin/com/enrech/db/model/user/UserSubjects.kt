package com.enrech.db.model.user

import com.enrech.common.BaseMapper
import com.enrech.db.model.content.*
import com.enrech.db.model.user.User.Companion.optionalReferrersOn
import com.enrech.db.model.user.User.Companion.referrersOn
import com.enrech.db.model.user.UserChapter.Companion.referrersOn
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
data class UserSubjectEntity(
    val subjectId: String,
    val chapterViewed: Int,
    val lessonViewed: Int,
    val totalChapter: Long,
    val totalLesson: Long,
    val completed: Boolean,
    val currentChapterId: String?,
    val currentLessonId: String?
)

class UserSubject(id: EntityID<UUID>): UUIDEntity(id), BaseMapper<UserSubjectEntity> {
    companion object: UUIDEntityClass<UserSubject>(UserSubjects)
    var subjectId by UserSubjects.subject
    var userId by UserSubjects.user
    val subject by Subject referencedOn UserSubjects.subject
    val user by User referencedOn UserSubjects.user
    val chapters get() = UserChapter.find { UserChapters.subject eq this@UserSubject.subjectId }
    val chapterViewed get() = chapters.count { it.completed }
    val totalChapters get() = chapters.count()
    val lessonViewed get() = chapters.sumOf { it.lessonCompleted }
    val totalLessons get() = chapters.sumOf { it.totalLessons }
    val completed get() = chapters.all { it.completed }
    val currentChapter get() = chapters.sortedBy { it.lastUpdate }.firstOrNull { it.active }?.chapter
    val currentLesson get() = chapters.mapNotNull { it.activeLesson }.maxByOrNull { it.lastUpdate }?.lesson

    override fun mapTo(): UserSubjectEntity =
        UserSubjectEntity(
            subjectId = subjectId.toString(),
            chapterViewed = chapterViewed,
            lessonViewed = lessonViewed,
            totalChapter = totalChapters,
            totalLesson = totalLessons,
            completed = completed,
            currentChapterId = currentChapter?.id?.value?.toString(),
            currentLessonId = currentLesson?.id?.value?.toString()
        )
}

object UserSubjects: UUIDTable() {
    val user = uuid("user_id").uniqueIndex().references(Users.id, onDelete = ReferenceOption.CASCADE)
    val subject = uuid("subject_id").uniqueIndex().references(Subjects.id, onDelete = ReferenceOption.CASCADE)
}