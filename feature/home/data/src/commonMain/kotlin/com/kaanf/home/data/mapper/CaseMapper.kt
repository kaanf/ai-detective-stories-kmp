package com.kaanf.home.data.mapper

import com.kaanf.home.data.dto.BountySerializable
import com.kaanf.home.data.dto.CaseSerializable
import com.kaanf.home.data.dto.CostSerializable
import com.kaanf.home.data.dto.TemporaryCaseSerializable
import com.kaanf.home.data.dto.TemporaryCasesSerializable
import com.kaanf.home.data.dto.UserCasesSerializable
import com.kaanf.home.domain.model.Bounty
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.CaseDifficulty
import com.kaanf.home.domain.model.CaseStatus
import com.kaanf.home.domain.model.Cost
import com.kaanf.home.domain.model.TemporaryCase
import com.kaanf.home.domain.model.TemporaryQuests
import com.kaanf.home.domain.model.UserCases

fun UserCasesSerializable.toDomain(): UserCases {
    return UserCases(
        cases.map { it.toDomain() }
    )
}

fun TemporaryCasesSerializable.toDomain(): TemporaryQuests {
    return TemporaryQuests(
        cases.map { it.toDomain() }
    )
}

fun TemporaryCaseSerializable.toDomain(): TemporaryCase {
    return TemporaryCase(
        id = id,
        title = title,
        type = type,
        cost = cost.toDomain(),
        bounty = bounty.toDomain(),
        difficulty = when (difficulty) {
            "easy" -> CaseDifficulty.EASY
            "medium" -> CaseDifficulty.MEDIUM
            else -> CaseDifficulty.HARD
        }
    )
}

fun CaseSerializable.toDomain(): Case {
    return Case(
        id = id,
        title = title,
        description = description,
        type = type,
        cost = cost.toDomain(),
        bounty = bounty.toDomain(),
        difficulty = when (difficulty) {
            "easy" -> CaseDifficulty.EASY
            "medium" -> CaseDifficulty.MEDIUM
            else -> CaseDifficulty.HARD
        }
    )
}

fun CostSerializable.toDomain(): Cost {
    return Cost(
        energy = energy,
    )
}

fun BountySerializable.toDomain(): Bounty {
    return Bounty(
        energy = energy,
        gold = gold,
    )
}


