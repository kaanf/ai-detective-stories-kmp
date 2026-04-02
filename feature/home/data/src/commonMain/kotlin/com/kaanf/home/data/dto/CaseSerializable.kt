package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CaseSerializable(
    @SerialName("_id")
    val id: String,
    val title: String,
    val description: String,
    val difficulty: String,
    @SerialName("caseId")
    val type: String,
    @SerialName("type")
    val status: String,
    val cost: CostSerializable,
    val bounty: BountySerializable,
)
