package com.kaanf.character.presentation.di

import com.kaanf.character.presentation.createcharacter.CreateCharacterScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val characterPresentationModule =
    module {
        viewModelOf(::CreateCharacterScreenViewModel)
    }
