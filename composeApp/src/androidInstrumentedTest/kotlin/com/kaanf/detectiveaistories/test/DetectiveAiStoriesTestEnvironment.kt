package com.kaanf.detectiveaistories.test

import com.kaanf.core.domain.provider.DeviceIdProvider
import com.kaanf.core.domain.repository.SessionStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import org.koin.dsl.module

object DetectiveAiStoriesTestEnvironment {
    private val backend = DetectiveAiStoriesFakeBackend()
    private val sessionStorage = InMemorySessionStorage()

    fun reset() {
        backend.reset()
        sessionStorage.reset()
    }

    internal fun backendEngine(): HttpClientEngine {
        return MockEngine { request ->
            backend.handle(this, request)
        }
    }

    fun activationDeepLinkFor(email: String): String? = backend.activationDeepLinkFor(email)

    internal fun sessionStorage(): SessionStorage = sessionStorage
}

val detectiveAiStoriesTestModule =
    module {
        single<DeviceIdProvider> {
            object : DeviceIdProvider {
                override fun getDeviceId(): String = "instrumentation-test-device"
            }
        }

        single<HttpClientEngine> {
            DetectiveAiStoriesTestEnvironment.backendEngine()
        }

        single<SessionStorage> {
            DetectiveAiStoriesTestEnvironment.sessionStorage()
        }
    }
