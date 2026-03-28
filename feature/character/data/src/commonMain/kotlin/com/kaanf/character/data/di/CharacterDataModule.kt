package com.kaanf.character.data.di

import com.kaanf.character.data.repository.CharacterRepositoryImpl
import com.kaanf.character.domain.repository.CharacterRepository
import org.koin.dsl.module

val characterDataModule =
    module {
        single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    }
