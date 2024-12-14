package com.bemos.schooldiary.domain.models

data class AuthenticateUser(
    val email: String,
    val password: String
)