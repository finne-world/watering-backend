package com.watering.watering_backend.application.controller.front

import com.watering.watering_backend.application.TemplateMapping
import com.watering.watering_backend.domain.entity.form.SigninForm
import com.watering.watering_backend.domain.entity.form.UserForm
import com.watering.watering_backend.domain.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/users")
class UserFrontController(
    private val userService: UserService
) {
    @GetMapping("signup")
    fun getCreate(
        @ModelAttribute
        userForm: UserForm,
        model: Model
    ): String {
        return TemplateMapping.USER_CREATE.value
    }

    @PostMapping("signup")
    fun postCreate(
        @Validated
        @ModelAttribute
        userForm: UserForm,
        bindingResult: BindingResult,
    ): String {
        if (bindingResult.hasErrors()) {
            return TemplateMapping.USER_CREATE.value
        }

        this.userService.registerUser(
            userForm.username!!,
            userForm.password!!,
            listOf("USER")
        )

        return TemplateMapping.USER_CREATE_COMPLETED.value
    }

    @GetMapping("signin")
    fun getSignin(
        @ModelAttribute
        signinForm: SigninForm,
        @RequestParam("failed", required = false)
        failed: Boolean,
        model: Model
    ): String {
        model.addAttribute("isFailed", failed)
        return TemplateMapping.USER_SIGNIN.value
    }
}
