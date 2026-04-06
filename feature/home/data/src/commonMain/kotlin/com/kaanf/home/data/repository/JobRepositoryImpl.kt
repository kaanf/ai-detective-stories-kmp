package com.kaanf.home.data.repository

import com.kaanf.core.data.networking.get
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import com.kaanf.home.data.dto.JobsSerializable
import com.kaanf.home.data.mapper.toDomain
import com.kaanf.home.domain.model.Jobs
import com.kaanf.home.domain.repository.JobRepository
import io.ktor.client.HttpClient

class JobRepositoryImpl(
    private val httpClient: HttpClient
): JobRepository {
    override suspend fun getJobs(): Result<Jobs, DataError.Remote> {
        return httpClient.get<JobsSerializable>(
            route = "/jobs"
        ).map { jobs ->
            jobs.toDomain()
        }
    }
}
