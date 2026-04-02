package com.kaanf.home.data.repository

import com.kaanf.core.data.networking.get
import com.kaanf.core.data.networking.post
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import com.kaanf.home.data.dto.CaseSerializable
import com.kaanf.home.data.dto.PickCaseRequest
import com.kaanf.home.data.dto.TemporaryCasesSerializable
import com.kaanf.home.data.dto.UserCasesSerializable
import com.kaanf.home.data.mapper.toDomain
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.TemporaryQuests
import com.kaanf.home.domain.model.UserCases
import com.kaanf.home.domain.repository.CaseRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CaseRepositoryImpl(
    private val httpClient: HttpClient,
) : CaseRepository {
    override suspend fun pickCase(caseId: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/game/pick-quest",
            body = PickCaseRequest(
                temporaryQuestId = caseId
            ),
        )
    }

    override suspend fun getTemporaryCases(): Result<TemporaryQuests, DataError.Remote> {
        return httpClient.get<TemporaryCasesSerializable>(
            route = "/game/temporary-quests",
        ).map { temporaryCases ->
            temporaryCases.toDomain()
        }
    }

    override suspend fun getCases(): Result<UserCases, DataError.Remote> {
        return httpClient.get<UserCasesSerializable>(
            route = "/game/games",
            queryParams = mapOf("ended" to false),
        ).map { userCases ->
            userCases.toDomain()
        }
    }
}
