package com.watering.watering_backend.domain.aspect

import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.domain.service.DeviceService
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Aspect
@Component
class ControllerAspect(
    //TODO: Service を利用していいのか
    private val deviceService: DeviceService
) {
    @Before("""
        @annotation(com.watering.watering_backend.domain.annotation.aspect.CombinationOfUserAndDevice) &&
        args(userId, deviceId, ..)
    """)
    fun methodValidateCombination(userId: Long, deviceId: Long) {
        this.validateCombination(userId, deviceId)
    }

    @Before("""
        @within(com.watering.watering_backend.domain.annotation.aspect.CombinationOfUserAndDevice) &&
        args(userId, deviceId, ..)
    """)
    fun classValidateCombination(userId: Long, deviceId: Long) {
        this.validateCombination(userId, deviceId)
    }

    private fun validateCombination(userId: Long, deviceId: Long) {
        val device: DeviceEntity = this.deviceService.getById(deviceId)

        if (device.userId != userId) {
            throw ApplicationException(
                httpStatus = HttpStatus.BAD_REQUEST,
                error = Error.INVALID_COMBINATION_OF_DEVICE_AND_USER,
                errorDescription = "invalid combination of device id and user id."
            )
        }
    }
}
