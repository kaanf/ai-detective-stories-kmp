package com.kaanf.character.domain.repository

import com.kaanf.character.domain.model.avatar.UserAvatarList
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result

interface CharacterRepository {
    suspend fun getAvatars(): Result<UserAvatarList, DataError.Remote>
}

