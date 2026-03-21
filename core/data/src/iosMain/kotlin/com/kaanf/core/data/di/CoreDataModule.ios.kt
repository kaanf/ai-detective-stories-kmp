package com.kaanf.core.data.di

import com.kaanf.core.data.device.DeviceIdProvider
import com.kaanf.core.data.device.IosDeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataModule =
    module {
        single<HttpClientEngine> { Darwin.create() }
        single<DeviceIdProvider> { IosDeviceIdProvider() }
    }
