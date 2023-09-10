package com.enrech.db.dao.user

import com.enrech.db.model.user.UserBookmarkEntity

interface UserBookmarkDAOFacade {
    suspend fun allUserBookmarks(userId: String): List<UserBookmarkEntity>
    suspend fun bookmark(id: String): UserBookmarkEntity?
    suspend fun addNewBookmark(lessonId: String, userId: String, position: Long, content: String): UserBookmarkEntity?
    suspend fun editBookmark(id: String, content: String): Boolean
    suspend fun deleteBookmark(id: String): Boolean
}