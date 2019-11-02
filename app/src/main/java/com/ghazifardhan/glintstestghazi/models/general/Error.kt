package com.ghazifardhan.glintstestghazi.models.general

data class Error(
    val errors: Errors,
    val message: String,
    val statusCode: Int
)