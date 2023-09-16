package com.enrech.db.model.user

import com.enrech.db.model.user.UserBadge.Companion.referrersOn
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID


class UserView(id: EntityID<UUID>): UUIDEntity(id) {
    val user by User referencedOn UserViews.user
    val views by UserLesson referrersOn UserViews.views
    val subjects = views.map { it.lesson.group.chapter.subject }
}

object UserViews: UUIDTable() {
    val user = reference("user", Users)
    val views = reference("views", UserLessons)

    init {
        index(true, user)
    }
}