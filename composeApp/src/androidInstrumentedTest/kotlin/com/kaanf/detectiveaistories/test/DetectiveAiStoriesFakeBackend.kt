package com.kaanf.detectiveaistories.test

import com.kaanf.auth.data.dto.request.LoginRequest
import com.kaanf.auth.data.dto.request.RegisterRequest
import com.kaanf.character.data.dto.UserAvatarListSerializable
import com.kaanf.character.data.dto.UserAvatarSerializable
import com.kaanf.core.data.dto.AuthInfoSerializable
import com.kaanf.core.data.dto.UserSerializable
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.content.OutgoingContent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DetectiveAiStoriesFakeBackend(
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    private val registeredUsers = linkedMapOf<String, RegisteredUser>()

    fun reset() {
        registeredUsers.clear()
    }

    suspend fun handle(scope: MockRequestHandleScope, request: HttpRequestData) =
        with(scope) {
            when (request.url.encodedPath) {
                "/api/auth/register" -> handleRegister(request)
                "/api/auth/login" -> handleLogin(request)
                "/api/notification/activate-user" -> handleActivateUser(request)
                "/api/user/get-profile-images" -> handleGetProfileImages()
                else -> respond(
                    content = """{"error":"Unhandled route: ${request.method.value} ${request.url.encodedPath}"}""",
                    status = HttpStatusCode.NotFound,
                    headers = jsonHeaders,
                )
            }
        }

    private suspend fun MockRequestHandleScope.handleRegister(request: HttpRequestData) =
        run {
            val payload = json.decodeFromString<RegisterRequest>(request.bodyAsText())
            registeredUsers[payload.email] =
                RegisteredUser(
                    email = payload.email,
                    password = payload.password,
                    verificationToken = "token-${payload.email.hashCode()}",
                )

            respond(
                content = "",
                status = HttpStatusCode.OK,
                headers = emptyHeaders,
            )
        }

    private suspend fun MockRequestHandleScope.handleLogin(request: HttpRequestData) =
        run {
            val payload = json.decodeFromString<LoginRequest>(request.bodyAsText())
            val user = registeredUsers[payload.email]

            if (user == null || user.password != payload.password) {
                respond(
                    content = """{"error":"Invalid credentials"}""",
                    status = HttpStatusCode.Unauthorized,
                    headers = jsonHeaders,
                )
            } else if (!user.isVerified) {
                respond(
                    content = """{"error":"Email is not verified"}""",
                    status = HttpStatusCode.Forbidden,
                    headers = jsonHeaders,
                )
            } else {
                respond(
                    content =
                        json.encodeToString(
                            AuthInfoSerializable(
                                accessToken = "test-access-token",
                                refreshToken = "test-refresh-token",
                                user =
                                    UserSerializable(
                                        id = "user-${user.email.hashCode()}",
                                        email = user.email,
                                        fullName = user.fullName,
                                        profileImageUrl = "",
                                        gameToken = 42,
                                        energy = 100,
                                    ),
                            ),
                        ),
                    status = HttpStatusCode.OK,
                    headers = jsonHeaders,
                )
            }
        }

    private suspend fun MockRequestHandleScope.handleActivateUser(request: HttpRequestData) =
        run {
            val token = request.url.parameters["token"]
            val matchingUser =
                registeredUsers.entries.firstOrNull { (_, user) ->
                    user.verificationToken == token
                }

            if (token.isNullOrBlank() || matchingUser == null) {
                respond(
                    content = """{"error":"Invalid token"}""",
                    status = HttpStatusCode.BadRequest,
                    headers = jsonHeaders,
                )
            } else {
                registeredUsers[matchingUser.key] = matchingUser.value.copy(isVerified = true)
                respond(
                    content = "",
                    status = HttpStatusCode.OK,
                    headers = emptyHeaders,
                )
            }
        }

    private suspend fun MockRequestHandleScope.handleGetProfileImages() =
        respond(
            content =
                json.encodeToString(
                    UserAvatarListSerializable(
                        images =
                            listOf(
                                UserAvatarSerializable(
                                    id = "avatar-1",
                                    imageUrl = "https://example.com/avatar-1.png",
                                ),
                                UserAvatarSerializable(
                                    id = "avatar-2",
                                    imageUrl = "https://example.com/avatar-2.png",
                                ),
                            ),
                    ),
                ),
            status = HttpStatusCode.OK,
            headers = jsonHeaders,
        )

    private fun HttpRequestData.bodyAsText(): String {
        return when (val requestBody = body) {
            is OutgoingContent.ByteArrayContent -> requestBody.bytes().decodeToString()
            is OutgoingContent.ReadChannelContent -> error("ReadChannelContent is not supported in tests")
            is OutgoingContent.WriteChannelContent -> error("WriteChannelContent is not supported in tests")
            is OutgoingContent.NoContent -> ""
            is OutgoingContent.ProtocolUpgrade -> error("ProtocolUpgrade is not supported in tests")
            is MultiPartFormDataContent -> error("Multipart is not supported in tests")
            is OutgoingContent.ContentWrapper -> error("Wrapped content is not supported in tests")
            else -> error("Unsupported request body type: ${requestBody::class.qualifiedName}")
        }
    }

    private data class RegisteredUser(
        val email: String,
        val password: String,
        val fullName: String = "Detective",
        val verificationToken: String,
        val isVerified: Boolean = false,
    )

    fun activationDeepLinkFor(email: String): String? {
        val token = registeredUsers[email]?.verificationToken ?: return null
        return "ads://ads.kaanf.com/api/notification/activate-user?token=$token"
    }

    companion object {
        private val jsonHeaders =
            headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        private val emptyHeaders = headersOf()
    }
}
