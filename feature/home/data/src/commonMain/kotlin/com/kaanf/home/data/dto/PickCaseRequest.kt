package com.kaanf.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PickCaseRequest(
    val temporaryQuestId: String,
)
