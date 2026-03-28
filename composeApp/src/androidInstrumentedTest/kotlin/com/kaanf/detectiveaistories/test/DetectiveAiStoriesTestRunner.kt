package com.kaanf.detectiveaistories.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class DetectiveAiStoriesTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context,
    ): Application {
        return super.newApplication(
            cl,
            DetectiveAiStoriesTestApplication::class.java.name,
            context,
        )
    }
}
