package com.kaanf.home.domain.repository

import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.home.domain.model.PubItem

interface PubRepository {
    suspend fun getPubItems(): Result<List<PubItem>, DataError.Remote>
}
