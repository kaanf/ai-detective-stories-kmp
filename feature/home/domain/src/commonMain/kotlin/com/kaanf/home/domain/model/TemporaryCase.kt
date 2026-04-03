package com.kaanf.home.domain.model

data class TemporaryCase(
    val id: String,
    val title: String,
    val difficulty: CaseDifficulty,
    val type: String,
    val cost: Cost,
    val bounty: Bounty,
    val isPicked: Boolean
)
