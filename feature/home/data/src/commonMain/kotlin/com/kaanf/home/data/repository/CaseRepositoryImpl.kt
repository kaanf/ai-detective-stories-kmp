package com.kaanf.home.data.repository

import com.kaanf.core.data.networking.get
import com.kaanf.core.data.networking.post
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.data.dto.CaseSerializable
import com.kaanf.home.data.dto.PickCaseRequest
import com.kaanf.home.data.dto.TemporaryCasesSerializable
import com.kaanf.home.data.dto.UserCasesSerializable
import com.kaanf.home.data.mapper.toDomain
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.TemporaryCase
import com.kaanf.home.domain.model.TemporaryQuests
import com.kaanf.home.domain.model.UserCases
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.CaseStore
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class CaseRepositoryImpl(
    private val httpClient: HttpClient,
    private val caseStore: CaseStore,
) : CaseRepository {
    override suspend fun getTemporaryCases(): Result<TemporaryQuests, DataError.Remote> {
        return httpClient.get<TemporaryCasesSerializable>(
            route = "/game/temporary-quests",
        ).map { temporaryCases ->
            temporaryCases.toDomain()
        }.onSuccess {
            caseStore.updateTemporaryCases(it.cases)
        }
    }

    override suspend fun getCases(): Result<UserCases, DataError.Remote> {
        return httpClient.get<UserCasesSerializable>(
            route = "/game/games",
            queryParams = mapOf("ended" to false),
        ).map { userCases ->
            userCases.toDomain()
        }.onSuccess {
            caseStore.updateUserCases(it.cases)
        }
    }

    override fun observeTemporaryCases(): Flow<List<TemporaryCase>> {
        return caseStore.observeTemporaryCases()
    }

    override fun observeUserCases(): Flow<List<Case>> {
        return caseStore.observeUserCases()
    }

    override suspend fun pickCase(caseId: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/game/pick-quest",
            body = PickCaseRequest(
                temporaryQuestId = caseId
            ),
        )
    }
}
