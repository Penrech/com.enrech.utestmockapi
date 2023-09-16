package com.enrech.db.dao.di

import com.enrech.db.dao.content.*
import org.koin.dsl.module

val contentDaoModule = module {
    single<LessonsDAOFacade> { LessonsDAOFacadeImpl() }
    single<LessonGroupDAOFacade> { LessonGroupDAOFacadeImpl() }
    single<ChapterDAOFacade> { ChapterDAOFacadeImpl() }
    single<SubjectDAOFacade> { SubjectDAOFacadeImpl() }
}