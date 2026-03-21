package com.kaanf.detectiveaistories

import android.app.Application
import com.kaanf.detectiveaistories.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class DetectiveAiStoriesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DetectiveAiStoriesApplication)
            androidLogger()
        }
    }
}
