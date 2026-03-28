package com.kaanf.core.domain.repository

import com.kaanf.core.domain.model.user.User
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult

interface UserRepository {
    suspend fun updateUser(user: User): EmptyResult<DataError.Remote>
}
