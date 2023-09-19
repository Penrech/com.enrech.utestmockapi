package com.enrech.db.model.user

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction

fun Transaction.addUserTables() = SchemaUtils.create(Users, UserViews, UserViewsGroup, UserBookmarks, UserBadges, UserRewards)