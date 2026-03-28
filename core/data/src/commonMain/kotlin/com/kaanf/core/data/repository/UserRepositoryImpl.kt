package com.kaanf.core.data.repository

import com.kaanf.core.data.dto.GenericBooleanResponse
import com.kaanf.core.data.dto.UserSerializable
import com.kaanf.core.data.mappers.toSerializable
import com.kaanf.core.data.networking.put
import com.kaanf.core.domain.model.user.User
import com.kaanf.core.domain.repository.UserRepository
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.asEmptyResult
import io.ktor.client.HttpClient

class UserRepositoryImpl(
    private val httpClient: HttpClient
): UserRepository {
    override suspend fun updateUser(user: User): EmptyResult<DataError.Remote> {
        return httpClient.put<UserSerializable, GenericBooleanResponse>(
            route = "/user",
            body = user.toSerializable()
        ).asEmptyResult()
    }
}
