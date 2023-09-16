package com.enrech.domain.repository

import com.enrech.db.model.content.SubjectEntity

interface SubjectRepository {
    suspend fun allSubjects(): List<SubjectEntity>
    suspend fun subject(id: String): SubjectEntity?
    suspend fun addNewSubject(title: String): SubjectEntity?
    suspend fun editSubject(id: String, title: String): Boolean
    suspend fun deleteSubject(id: String): Boolean
}