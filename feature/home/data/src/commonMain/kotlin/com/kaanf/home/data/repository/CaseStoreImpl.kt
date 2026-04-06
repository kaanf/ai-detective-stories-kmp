package com.kaanf.home.data.repository

import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.TemporaryCase
import com.kaanf.home.domain.repository.CaseStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaseStoreImpl : CaseStore {
    private val temporaryCases = MutableStateFlow<List<TemporaryCase>>(emptyList())
    private val userCases = MutableStateFlow<List<Case>>(emptyList())

    override fun observeTemporaryCases(): Flow<List<TemporaryCase>> {
        return temporaryCases.asStateFlow()
    }

    override fun observeUserCases(): Flow<List<Case>> {
        return userCases.asStateFlow()
    }

    override suspend fun updateTemporaryCases(cases: List<TemporaryCase>) {
        temporaryCases.value = cases
    }

    override suspend fun updateUserCases(cases: List<Case>) {
        userCases.value = cases
    }
}
