package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CaseSerializable(
    val id: String,
    val title: String,
    val difficulty: String,
    @SerialName("caseType")
    val type: String,
    val status: String,
    val cost: CostSerializable,
    val bounty: BountySerializable,
)
