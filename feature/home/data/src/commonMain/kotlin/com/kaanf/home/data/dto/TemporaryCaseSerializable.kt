package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemporaryCaseSerializable(
    @SerialName("_id")
    val id: String,
    val title: String,
    val difficulty: String,
    @SerialName("caseId")
    val type: String,
    val cost: CostSerializable,
    val bounty: BountySerializable,
)
