package com.enrech.data.di

import com.enrech.data.repository.*
import com.enrech.db.dao.user.UserDAOFacade
import com.enrech.domain.repository.*
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ViewsRepository> { ViewsRepositoryImpl(get()) }
    single<RewardsRepository> { RewardsRepositoryImpl(get()) }
    single<BookmarksRepository> { BookmarksRepositoryImpl(get()) }
    single<SubjectRepository> { SubjectRepositoryImpl(get()) }
    single<ChapterRepository> { ChapterRepositoryImpl(get()) }
    single<LessonGroupRepository> { LessonGroupRepositoryImpl(get()) }
    single<LessonRepository> { LessonRepositoryImpl(get()) }
}