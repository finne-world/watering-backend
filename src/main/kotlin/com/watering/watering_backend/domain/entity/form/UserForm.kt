package com.watering.watering_backend.domain.entity.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UserForm(
    @field: NotEmpty(message = "名前を入力してください")
    @field: Size(min = 2, max = 32, message = "ユーザー名は2文字以上32文字以下で入力してください")
    @field: Pattern(regexp = "[0-9a-zA-Z-_]*", message = "ユーザー名に使用できるのは半角英数字と記号(-_)のみです")
    val username: String? = null,
    @field: NotEmpty(message = "パスワードを入力してください")
    @field: Size(min = 4, max = 32, message = "パスワードは4文字以上32文字以下で入力してください")
    @field: Pattern(regexp = "[0-9a-zA-Z-_]*", message = "パスワードに使用できるのは半角英数字と記号(-_)のみです")
    val password: String? = null
)
