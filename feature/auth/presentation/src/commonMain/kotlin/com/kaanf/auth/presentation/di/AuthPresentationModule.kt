package com.kaanf.auth.presentation.di

import com.kaanf.auth.presentation.email_verification.loading.EmailVerificationLoadingViewModel
import com.kaanf.auth.presentation.email_verification.verification_result.EmailVerificationResultViewModel
import com.kaanf.auth.presentation.email_verification.verification_sent.EmailVerificationSentViewModel
import com.kaanf.auth.presentation.login.LoginViewModel
import com.kaanf.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::EmailVerificationSentViewModel)
        viewModelOf(::EmailVerificationLoadingViewModel)
        viewModelOf(::EmailVerificationResultViewModel)
    }
