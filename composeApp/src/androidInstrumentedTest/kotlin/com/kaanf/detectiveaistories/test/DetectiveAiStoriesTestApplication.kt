package com.kaanf.detectiveaistories.test

import android.app.Application
import com.kaanf.detectiveaistories.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class DetectiveAiStoriesTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DetectiveAiStoriesTestEnvironment.reset()

        initKoin(
            config = {
                androidContext(this@DetectiveAiStoriesTestApplication)
                androidLogger()
                allowOverride(true)
            },
            additionalModules = listOf(detectiveAiStoriesTestModule),
        )
    }
}
