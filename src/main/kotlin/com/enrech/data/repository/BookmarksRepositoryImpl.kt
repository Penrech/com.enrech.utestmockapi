package com.enrech.data.repository

import com.enrech.db.dao.user.UserBookmarkDAOFacade
import com.enrech.db.model.user.UserBookmarkEntity
import com.enrech.domain.repository.BookmarksRepository

class BookmarksRepositoryImpl(private val dao: UserBookmarkDAOFacade) : BookmarksRepository {
    override suspend fun allUserBookmarks(userId: String): List<UserBookmarkEntity> = dao.allUserBookmarks(userId)

    override suspend fun bookmark(id: String): UserBookmarkEntity? = dao.bookmark(id)

    override suspend fun addNewBookmark(
        lessonId: String,
        userId: String,
        position: Long,
        content: String
    ): UserBookmarkEntity? = dao.addNewBookmark(lessonId, userId, position, content)

    override suspend fun editBookmark(id: String, content: String): Boolean = dao.editBookmark(id, content)

    override suspend fun deleteBookmark(id: String): Boolean = dao.deleteBookmark(id)
}