package com.kaanf.home.domain.repository

import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.home.domain.model.Jobs

interface JobRepository {
    suspend fun getJobs(): Result<Jobs, DataError.Remote>
}
