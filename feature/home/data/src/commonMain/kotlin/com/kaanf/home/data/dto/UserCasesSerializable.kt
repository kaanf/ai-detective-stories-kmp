package com.kaanf.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCasesSerializable(
    @SerialName("games")
    val cases: List<CaseSerializable>
)
