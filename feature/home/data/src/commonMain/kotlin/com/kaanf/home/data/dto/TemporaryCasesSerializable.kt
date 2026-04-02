package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemporaryCasesSerializable(
    @SerialName("temporaryQuests")
    val cases: List<TemporaryCaseSerializable>
)
