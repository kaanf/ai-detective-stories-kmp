package com.kaanf.core.data.di

import com.kaanf.core.data.auth.DataStoreSessionStorage
import com.kaanf.core.data.auth.KtorAuthService
import com.kaanf.core.data.logging.KermitLogger
import com.kaanf.core.data.networking.HttpClientFactory
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.auth.SessionStorage
import com.kaanf.core.domain.logging.DetectiveAiStoriesLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule =
    module {
        includes(platformCoreDataModule)
        single<DetectiveAiStoriesLogger> { KermitLogger }
        single {
            HttpClientFactory(
                get(), get()
            ).create(get())
        }
        singleOf(::KtorAuthService) bind AuthService::class
        singleOf(::DataStoreSessionStorage) bind SessionStorage::class
    }
