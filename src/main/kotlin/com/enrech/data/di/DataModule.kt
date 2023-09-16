package com.enrech.data.di

import com.enrech.data.repository.*
import com.enrech.db.dao.user.UserDAOFacade
import org.koin.dsl.module

val dataModule = module {
    single { UserRepositoryImpl(get()) }
    single { ViewsRepositoryImpl(get()) }
    single { RewardsRepositoryImpl(get()) }
    single { BookmarksRepositoryImpl(get()) }
    single { SubjectRepositoryImpl(get()) }
    single { ChapterRepositoryImpl(get()) }
    single { LessonGroupRepositoryImpl(get()) }
    single { LessonRepositoryImpl(get()) }
}