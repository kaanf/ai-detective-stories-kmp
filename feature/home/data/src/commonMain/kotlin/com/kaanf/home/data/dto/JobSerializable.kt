package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobSerializable(
    @SerialName("name") val type: String,
    val nextRunAt: String,
    val timeUntilNextRun: String
)

@Serializable
data class JobsSerializable(
    val jobs: List<JobSerializable>
)

