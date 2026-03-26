package com.kaanf.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kaanf.core.data.auth.createDataStore
import com.kaanf.core.data.device.AndroidDeviceIdProvider
import com.kaanf.core.data.device.DeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformCoreDataModule =
    module {
        single<HttpClientEngine> { OkHttp.create() }
        single<DeviceIdProvider> { AndroidDeviceIdProvider(get()) }
        single<DataStore<Preferences>> {
            createDataStore(androidContext())
        }
    }
