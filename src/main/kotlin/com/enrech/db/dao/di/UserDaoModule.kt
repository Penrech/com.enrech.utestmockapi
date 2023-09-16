package com.enrech.db.dao.di

import com.enrech.db.dao.user.*
import com.enrech.db.model.user.UserBookmark
import org.koin.dsl.module

val userDaoModule = module {
    single<UserDAOFacade> { UserDAOFacadeImpl() }
    single<UserBookmarkDAOFacade> { UserBookmarkDAOFacadeImpl() }
    single<UserRewardsDAOFacade> { UserRewardsDAOFacadeImpl() }
    single<UserViewDAOFacade> { UserViewDAOFacadeImpl() }
}