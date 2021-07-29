package com.watering.watering_backend.domain.exception

class AuthorityNotFoundException(val invalidAuthorityNames: List<String>): ResourceNotFoundException(
    "Invalid authority name. [${invalidAuthorityNames.joinToString(", ")}]"
)
