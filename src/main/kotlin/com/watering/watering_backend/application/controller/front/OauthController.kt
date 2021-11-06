package com.watering.watering_backend.application.controller.front

import com.watering.watering_backend.application.controller.helper.redirect
import com.watering.watering_backend.domain.service.DiscordService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/oauth")
class OauthController(
    private val discordService: DiscordService
) {
    @GetMapping("/discord/authorize")
    fun getAuthorizeDiscord(): String {
        val authorizeUrl: String = this.discordService.getAuthorizeUrl()

        return redirect(authorizeUrl)
    }
}
