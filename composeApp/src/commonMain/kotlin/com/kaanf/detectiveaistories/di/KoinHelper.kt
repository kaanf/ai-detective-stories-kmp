package com.kaanf.detectiveaistories.di

import com.kaanf.auth.data.di.authDataModule
import com.kaanf.auth.presentation.di.authPresentationModule
import com.kaanf.character.data.di.characterDataModule
import com.kaanf.character.presentation.di.characterPresentationModule
import com.kaanf.core.data.di.coreDataModule
import com.kaanf.home.presentation.di.homePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authDataModule,
            authPresentationModule,
            characterDataModule,
            characterPresentationModule,
            homePresentationModule,
            appModule,
        )
    }
}
