package com.watering.watering_backend.application.controller

import com.watering.watering_backend.application.ApplicationUrl
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.security.model.UserDetailsImpl
import com.watering.watering_backend.domain.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class ControllerAdviser(
    private val userService: UserService
) {
    @ModelAttribute
    fun setAuthenticationPrincipal(
        @AuthenticationPrincipal
        user: UserDetailsImpl?,
        model: Model
    ) {
        if (user == null) {
            return
        }
        model.addAttribute("user", this.userService.getById(user.getId()))
    }

    @ModelAttribute
    fun setTemplateMapping(
        model: Model
    ) {
        model.addAttribute("urls", ApplicationUrl)
    }
}
