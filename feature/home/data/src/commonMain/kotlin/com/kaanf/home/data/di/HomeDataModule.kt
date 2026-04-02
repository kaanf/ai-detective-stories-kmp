package com.kaanf.home.data.di

import com.kaanf.home.data.repository.CaseRepositoryImpl
import com.kaanf.home.data.repository.PubRepositoryImpl
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.PubRepository
import org.koin.dsl.module

val homeDataModule =
    module {
        single<CaseRepository> { CaseRepositoryImpl(get()) }
        single<PubRepository> { PubRepositoryImpl(get()) }
    }
