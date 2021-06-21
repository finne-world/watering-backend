package com.watering.watering_backend.application.controller.helper

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.toOption
import org.springframework.core.MethodParameter
import org.springframework.web.bind.annotation.RequestParam

fun getRequestParamName(parameter: MethodParameter): String {
    val requestParamAnnotation: Option<RequestParam> = parameter.parameterAnnotations.find { it is RequestParam }.toOption().map { it as RequestParam }

    requestParamAnnotation.getOrElse {
        throw IllegalStateException("${parameter.parameterName} has no annotation `RequestParam`")
    }.also {
        return it.value
    }
}
