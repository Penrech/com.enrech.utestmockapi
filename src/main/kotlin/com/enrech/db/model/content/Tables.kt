package com.enrech.db.model.content

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction

fun Transaction.addContentTables() = SchemaUtils.create(Lessons, LessonGroups, Chapters, Subjects)