package com.kaanf.detectiveaistories.test

import com.kaanf.core.domain.model.auth.AuthInfo
import com.kaanf.core.domain.repository.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySessionStorage : SessionStorage {
    private val authInfo = MutableStateFlow<AuthInfo?>(null)

    override fun observeAuthInfo(): Flow<AuthInfo?> = authInfo

    override suspend fun set(info: AuthInfo?) {
        authInfo.value = info
    }

    fun reset() {
        authInfo.value = null
    }
}
