package com.kaanf.character.data.repository

import com.kaanf.character.data.dto.UserAvatarListSerializable
import com.kaanf.character.data.mapper.toDomain
import com.kaanf.character.domain.model.avatar.UserAvatarList
import com.kaanf.character.domain.repository.CharacterRepository
import com.kaanf.core.data.networking.get
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import io.ktor.client.HttpClient

class CharacterRepositoryImpl(
    private val httpClient: HttpClient
): CharacterRepository {
    override suspend fun getAvatars(): Result<UserAvatarList, DataError.Remote> {
        return httpClient.get<UserAvatarListSerializable>(
            route = "/user/get-profile-images",
        ).map { avatarListSerializable ->
            avatarListSerializable.toDomain()
        }
    }
}
