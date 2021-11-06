package com.watering.watering_backend.domain.service.impl

import com.watering.watering_backend.config.property.OauthProperties
import com.watering.watering_backend.domain.service.DiscordService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class DiscordServiceImpl(
    private val httpClient: OkHttpClient,
    private val oauthProperties: OauthProperties
): DiscordService {
    override fun getAuthorizeUrl(): String {
        val authorizeUrl: HttpUrl = HttpUrl.Builder().also {
            it.addQueryParameter("response_type", "code")
            it.addQueryParameter("scope", "identify")
            it.addQueryParameter("client_id", this.oauthProperties.discord.clientId)
            it.addQueryParameter("redirect_uri", this.oauthProperties.discord.redirectUri)
        }.build()

        val request: Request = Request.Builder()
                                      .url(authorizeUrl)
                                      .build()

        this.httpClient.newCall(request).execute().use { response: Response ->
            //TODO: エラーハンドリングやりなおす
            if (response.isSuccessful.not()) {
                throw IllegalStateException("error")
            }

            val body: String = response.body?.use { it.string() } ?: "nothing"
            println(body)

            return body
        }
    }
}
