package com.watering.watering_backend.lib.aws

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import org.springframework.stereotype.Component

@Component
class AWSParameterStore(
    private val awsSimpleSystemsManagement: AWSSimpleSystemsManagement
) {
    fun getParameter(parameterName: String): String {
        val request: GetParameterRequest = GetParameterRequest().also {
            it.name = parameterName
            it.withDecryption = true
        }

        return this.awsSimpleSystemsManagement.getParameter(request).parameter.value
    }
}
