package com.kaanf.home.domain.repository

import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.TemporaryCase
import com.kaanf.home.domain.model.TemporaryQuests
import com.kaanf.home.domain.model.UserCases
import kotlinx.coroutines.flow.Flow

interface CaseRepository {
    fun observeTemporaryCases(): Flow<List<TemporaryCase>>
    fun observeUserCases(): Flow<List<Case>>
    suspend fun pickCase(caseId: String): EmptyResult<DataError.Remote>
    suspend fun getTemporaryCases(): Result<TemporaryQuests, DataError.Remote>
    suspend fun getCases(): Result<UserCases, DataError.Remote>
}
