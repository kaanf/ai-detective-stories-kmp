package com.kaanf.home.presentation.di

import com.kaanf.home.presentation.dashboard.DashboardViewModel
import com.kaanf.home.presentation.dispatch.DispatchViewModel
import com.kaanf.home.presentation.pub.PubViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule =
    module {
        viewModelOf(::DashboardViewModel)
        viewModelOf(::DispatchViewModel)
        viewModelOf(::PubViewModel)
    }
