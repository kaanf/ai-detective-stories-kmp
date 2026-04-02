package com.kaanf.home.data.mapper

import com.kaanf.home.data.dto.PubItemSerializable
import com.kaanf.home.domain.model.PubItem
import com.kaanf.home.domain.model.PubItemAccent

fun PubItemSerializable.toDomain(): PubItem =
    PubItem(
        id = id,
        name = name,
        subtitle = subtitle,
        description = description,
        energyBonus = energyBonus,
        actionCost = actionCost,
        actionLabel = actionLabel,
        accentType = when (accentType.uppercase()) {
            "RED" -> PubItemAccent.RED
            else -> PubItemAccent.GOLD
        },
    )
