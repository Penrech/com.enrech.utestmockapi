package com.enrech.data.repository

import com.enrech.db.dao.content.SubjectDAOFacade
import com.enrech.db.model.content.SubjectEntity
import com.enrech.domain.repository.SubjectRepository

class SubjectRepositoryImpl(private val dao: SubjectDAOFacade) : SubjectRepository {
    override suspend fun allSubjects(): List<SubjectEntity> = dao.allSubjects()

    override suspend fun subject(id: String): SubjectEntity? = dao.subject(id)

    override suspend fun addNewSubject(title: String, acronym: String): SubjectEntity? = dao.addNewSubject(title, acronym)

    override suspend fun editSubject(id: String, title: String): Boolean = dao.editSubject(id, title)

    override suspend fun deleteSubject(id: String): Boolean = dao.deleteSubject(id)
}