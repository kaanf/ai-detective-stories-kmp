package com.kaanf.core.domain.home

import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result

interface HomeService {
    suspend fun getAvatars(): Result<UserAvatarList, DataError.Remote>
}
