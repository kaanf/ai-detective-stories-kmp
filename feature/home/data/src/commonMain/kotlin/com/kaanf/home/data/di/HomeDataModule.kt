package com.kaanf.home.data.di

import com.kaanf.home.data.repository.CaseRepositoryImpl
import com.kaanf.home.data.repository.CaseStoreImpl
import com.kaanf.home.data.repository.JobRepositoryImpl
import com.kaanf.home.data.repository.PubRepositoryImpl
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.CaseStore
import com.kaanf.home.domain.repository.JobRepository
import com.kaanf.home.domain.repository.PubRepository
import com.kaanf.home.domain.usecase.PickCaseAndSyncUseCase
import org.koin.dsl.module

val homeDataModule =
    module {
        single<JobRepository> { JobRepositoryImpl(get()) }
        single<CaseStore> { CaseStoreImpl() }
        single<CaseRepository> { CaseRepositoryImpl(get(), get()) }
        single<PubRepository> { PubRepositoryImpl(get()) }
        factory { PickCaseAndSyncUseCase(get(), get()) }
    }
