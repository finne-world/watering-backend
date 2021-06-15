package com.watering.watering_backend.application.json.response

import org.springframework.http.HttpStatus

abstract class OkResponse: JsonResponse(HttpStatus.OK.value())
