package com.enrech.db.dao.user

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.user.User
import com.enrech.db.model.user.UserBookmark
import com.enrech.db.model.user.UserBookmarkEntity
import java.util.UUID

class UserBookmarkDAOFacadeImpl : UserBookmarkDAOFacade {
    override suspend fun allUserBookmarks(userId: String): List<UserBookmarkEntity> = dbQueryWithCatch {
        User[UUID.fromString(userId)].bookmarks.mapTo()
    } ?: emptyList()

    override suspend fun bookmark(id: String): UserBookmarkEntity? = dbQueryWithCatch {
        UserBookmark[UUID.fromString(id)].mapTo()
    }

    override suspend fun addNewBookmark(
        lessonId: String,
        userId: String,
        position: Long,
        content: String
    ): UserBookmarkEntity? = dbQueryWithCatch {
        UserBookmark.new {
            user = User[UUID.fromString(userId)]
            lesson = Lesson[UUID.fromString(lessonId)]
            bookmarkPosition = position
            description = content
        }.mapTo()
    }

    override suspend fun editBookmark(id: String, content: String): Boolean = dbQueryWithCatch {
        val bookmark = UserBookmark[UUID.fromString(id)]
        bookmark.description = content
        bookmark.flush()
    } ?: false

    override suspend fun deleteBookmark(id: String): Boolean = dbQueryWithCatch {
        UserBookmark[UUID.fromString(id)].delete()
        true
    } ?: false
}