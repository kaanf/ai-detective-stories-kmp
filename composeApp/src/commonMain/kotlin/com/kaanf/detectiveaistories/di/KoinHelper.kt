package com.kaanf.detectiveaistories.di

import com.kaanf.auth.presentation.di.authPresentationModule
import com.kaanf.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
        )
    }
}
