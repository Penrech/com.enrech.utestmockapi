package com.enrech.db.dao.content

import com.enrech.db.model.content.SubjectEntity

interface SubjectDAOFacade {
    suspend fun allSubjects(): List<SubjectEntity>
    suspend fun subject(id: String): SubjectEntity?
    suspend fun addNewSubject(title: String, acronym: String): SubjectEntity?
    suspend fun editSubject(id: String, title: String): Boolean
    suspend fun deleteSubject(id: String): Boolean
}