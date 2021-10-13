package com.watering.watering_backend.application.controller

import com.watering.watering_backend.application.ApplicationUrl
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class ControllerAdviser {
    @ModelAttribute
    fun setTemplateMapping(
        model: Model
    ) {
        model.addAttribute("urls", ApplicationUrl)
    }
}
