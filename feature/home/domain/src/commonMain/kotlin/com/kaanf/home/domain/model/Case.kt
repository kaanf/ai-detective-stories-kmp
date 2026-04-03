package com.kaanf.home.domain.model

data class Case(
    val id: String,
    val title: String,
    val difficulty: CaseDifficulty,
    val status: CaseStatus = CaseStatus.OPEN,
    val type: String,
    val cost: Cost,
    val bounty: Bounty,
)
