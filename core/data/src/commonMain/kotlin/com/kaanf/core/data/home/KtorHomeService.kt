package com.kaanf.core.data.home

import com.kaanf.core.data.dto.AvatarsSerializable
import com.kaanf.core.data.mappers.toDomain
import com.kaanf.core.data.networking.get
import com.kaanf.core.domain.home.HomeService
import com.kaanf.core.domain.home.UserAvatarList
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import io.ktor.client.HttpClient

class KtorHomeService(
    private val httpClient: HttpClient,
) : HomeService {
    override suspend fun getAvatars(): Result<UserAvatarList, DataError.Remote> {
        return httpClient.get<AvatarsSerializable>(
            route = "/user/get-profile-images",
        ).map { avatarsSerializable ->
            avatarsSerializable.toDomain()
        }
    }
}
