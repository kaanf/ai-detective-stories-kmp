package com.kaanf.home.data.repository

import com.kaanf.core.data.networking.get
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import com.kaanf.home.data.dto.PubItemSerializable
import com.kaanf.home.data.mapper.toDomain
import com.kaanf.home.domain.model.PubItem
import com.kaanf.home.domain.repository.PubRepository
import io.ktor.client.HttpClient

class PubRepositoryImpl(
    private val httpClient: HttpClient,
) : PubRepository {
    override suspend fun getPubItems(): Result<List<PubItem>, DataError.Remote> {
        return httpClient.get<List<PubItemSerializable>>(
            route = "/pub/items",
        ).map { items ->
            items.map { it.toDomain() }
        }
    }
}
