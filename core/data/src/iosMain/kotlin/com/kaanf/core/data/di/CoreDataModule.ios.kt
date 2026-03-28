package com.kaanf.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kaanf.core.data.auth.createDataStore
import com.kaanf.core.data.device.DeviceIdProvider
import com.kaanf.core.data.device.IosDeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataModule =
    module {
        single<HttpClientEngine> { Darwin.create() }
        single<DeviceIdProvider> { IosDeviceIdProvider() }
        single<DataStore<Preferences>> { createDataStore() }
    }
