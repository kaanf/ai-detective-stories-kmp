package com.kaanf.detectiveaistories.di

import com.kaanf.detectiveaistories.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf(::MainViewModel)
    }
