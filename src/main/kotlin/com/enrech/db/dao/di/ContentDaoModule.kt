package com.enrech.db.dao.di

import com.enrech.db.dao.content.*
import org.koin.dsl.module

val contentDaoModule = module {
    single { LessonsDAOFacadeImpl() }
    single { LessonGroupDAOFacadeImpl() }
    single { ChapterDAOFacadeImpl() }
    single { SubjectDAOFacadeImpl() }
}