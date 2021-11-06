package com.watering.watering_backend.lib.extension

import okhttp3.FormBody
import okhttp3.RequestBody

fun Map<String, String>.toRequestBody(): RequestBody {
    val formBuilder: FormBody.Builder = FormBody.Builder()
    this.forEach(formBuilder::add)

    return formBuilder.build()
}
