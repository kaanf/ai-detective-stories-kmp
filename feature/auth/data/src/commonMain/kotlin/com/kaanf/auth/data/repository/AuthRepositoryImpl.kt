package com.kaanf.auth.data.repository

import com.kaanf.auth.data.dto.request.EmailRequest
import com.kaanf.core.data.dto.AuthInfoSerializable
import com.kaanf.auth.data.dto.request.LoginRequest
import com.kaanf.auth.data.dto.request.RegisterRequest
import com.kaanf.core.data.mappers.toDomain
import com.kaanf.core.data.networking.get
import com.kaanf.core.data.networking.post
import com.kaanf.core.domain.model.auth.AuthInfo
import com.kaanf.core.domain.provider.DeviceIdProvider
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.map
import com.kaanf.auth.domain.repository.AuthRepository
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val deviceIdProvider: DeviceIdProvider,
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body =
                RegisterRequest(
                    email = email,
                    password = password,
                ),
        ) {
            headers.append(
                "deviceId",
                deviceIdProvider.getDeviceId(),
            )
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = "/auth/login",
            body =
                LoginRequest(
                    email = email,
                    password = password,
                ),
        ).map { authInfoSerializable ->
            authInfoSerializable.toDomain()
        }
    }

    override suspend fun resendVerificationMail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(email = email),
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/notification/activate-user",
            queryParams = mapOf("token" to token),
        )
    }

    override suspend fun forgotPassword(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/notification/forget-password",
            body = EmailRequest(email = email),
        )
    }
}
