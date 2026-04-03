package com.kaanf.home.data.mapper

import com.kaanf.home.data.dto.JobSerializable
import com.kaanf.home.data.dto.JobsSerializable
import com.kaanf.home.domain.model.Job
import com.kaanf.home.domain.model.JobType
import com.kaanf.home.domain.model.Jobs

fun JobsSerializable.toDomain(): Jobs {
    return Jobs(
        jobs = jobs.map { it.toDomain() }
    )
}

fun JobSerializable.toDomain(): Job {
    return Job(
        type = when (type) {
            "energy_regen" -> JobType.Energy
            "quest_refresh" -> JobType.Case
            else -> JobType.Case
        },
        nextRunAt = nextRunAt,
        timeUntilNextRun = timeUntilNextRun
    )
}
