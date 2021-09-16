package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.controller.helper.convertTo
import com.watering.watering_backend.application.json.response.user.GetUsersResponse
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter
import com.watering.watering_backend.domain.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUsers(
        @RequestParam(name = "name", required = false) name: String?,
        @RequestParam(name = "discord_id", required = false) discordId: Long?
    ): GetUsersResponse {
        val users: List<UserEntity> = this.userService.getUsers(UserFilter(name, discordId))

        return GetUsersResponse(
            users = users.map(::convertTo)
        )
    }
}
