package com.enrech.db.dao.di

import com.enrech.db.dao.user.UserBookmarkDAOFacadeImpl
import com.enrech.db.dao.user.UserDAOFacadeImpl
import com.enrech.db.dao.user.UserRewardsDAOFacadeImpl
import com.enrech.db.dao.user.UserViewDAOFacadeImpl
import org.koin.dsl.module

val userDaoModule = module {
    single { UserDAOFacadeImpl() }
    single { UserBookmarkDAOFacadeImpl() }
    single { UserRewardsDAOFacadeImpl() }
    single { UserViewDAOFacadeImpl() }
}