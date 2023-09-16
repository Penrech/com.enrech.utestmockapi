package com.enrech.domain.repository

import com.enrech.db.model.user.UserBookmarkEntity

interface BookmarksRepository {
    suspend fun allUserBookmarks(userId: String): List<UserBookmarkEntity>
    suspend fun bookmark(id: String): UserBookmarkEntity?
    suspend fun addNewBookmark(lessonId: String, userId: String, position: Long, content: String): UserBookmarkEntity?
    suspend fun editBookmark(id: String, content: String): Boolean
    suspend fun deleteBookmark(id: String): Boolean
}