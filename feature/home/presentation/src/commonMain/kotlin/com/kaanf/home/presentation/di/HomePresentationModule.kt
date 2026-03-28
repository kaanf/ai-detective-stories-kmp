package com.kaanf.home.presentation.di

import com.kaanf.home.presentation.createcharacter.CreateCharacterScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule =
    module {
        viewModelOf(::CreateCharacterScreenViewModel)
    }
