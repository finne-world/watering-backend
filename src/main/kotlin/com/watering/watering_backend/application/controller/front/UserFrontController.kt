package com.watering.watering_backend.application.controller.front

import com.watering.watering_backend.domain.constant.TemplateMapping
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/front/users")
class UserFrontController(
    private val userService: UserService
) {
    @GetMapping("create")
    fun getCreate(
        @ModelAttribute
        userForm: UserForm = UserForm(),
        model: Model
    ): String {
        model.addAttribute("userForm", userForm)
        return TemplateMapping.USER_CREATE.value
    }

    @PostMapping("create")
    fun postCreate(
        @Validated
        @ModelAttribute
        userForm: UserForm,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userForm", userForm)
            return TemplateMapping.USER_CREATE.redirect()
        }

        this.userService.registerUser(
            userForm.username!!,
            userForm.password!!,
            listOf("USER")
        )

        return TemplateMapping.USER_CREATE_COMPLETED.value
    }
}
