package com.watering.watering_backend.domain.entity.form

import com.watering.watering_backend.domain.exception.UnmodifiedAttributeException
import com.watering.watering_backend.lib.extension.isNotNull

abstract class Form<E> {
    abstract fun getUnmodifiableProperties(entity: E): Map<PropertyName, FormEntityValueMapping>

    fun getErrors(entity: E): List<UnmodifiedAttributeException> {
        val errors: MutableList<UnmodifiedAttributeException> = mutableListOf()
        val unmodifiableProperties: Map<PropertyName, FormEntityValueMapping> = this.getUnmodifiableProperties(entity)

        unmodifiableProperties.forEach { (propertyName, valueMapping) ->
            val (formValue, entityValue) = valueMapping
            if (willEdit(formValue, entityValue)) {
                errors.add(UnmodifiedAttributeException(attributeName = propertyName.name))
            }
        }
        return errors
    }

    private fun willEdit(formValue: Any?, entityValue: Any): Boolean {
        return formValue.isNotNull() && formValue != entityValue
    }

    data class PropertyName(
        val name: String
    )
    data class FormEntityValueMapping(
        val formValue: Any?,
        val entityValue: Any
    )
}
