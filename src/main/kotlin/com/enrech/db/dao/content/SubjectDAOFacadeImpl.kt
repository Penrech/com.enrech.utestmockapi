package com.enrech.db.dao.content

import com.enrech.common.mapTo
import com.enrech.db.DatabaseFactory.dbQueryWithCatch
import com.enrech.db.model.content.Subject
import com.enrech.db.model.content.SubjectEntity
import java.util.*

class SubjectDAOFacadeImpl : SubjectDAOFacade {
    override suspend fun allSubjects(): List<SubjectEntity> = dbQueryWithCatch {
        Subject.all().mapTo()
    } ?: emptyList()

    override suspend fun subject(id: String): SubjectEntity? = dbQueryWithCatch {
        Subject[UUID.fromString(id)].mapTo()
    }

    override suspend fun addNewSubject(title: String): SubjectEntity? = dbQueryWithCatch {
        Subject.new {
            this.name = title
        }.mapTo()
    }

    override suspend fun editSubject(id: String, title: String): Boolean = dbQueryWithCatch {
        val subject = Subject[UUID.fromString(id)]
        subject.name = title
        subject.flush()
    } ?: false

    override suspend fun deleteSubject(id: String): Boolean = dbQueryWithCatch {
        Subject[UUID.fromString(id)].delete()
        true
    } ?: false
}