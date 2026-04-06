package com.kaanf.home.domain.repository

import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.TemporaryCase
import kotlinx.coroutines.flow.Flow

interface CaseStore {
    fun observeTemporaryCases(): Flow<List<TemporaryCase>>
    fun observeUserCases(): Flow<List<Case>>
    suspend fun updateTemporaryCases(cases: List<TemporaryCase>)
    suspend fun updateUserCases(cases: List<Case>)
}
