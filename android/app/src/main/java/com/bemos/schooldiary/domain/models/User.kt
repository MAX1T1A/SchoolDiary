package com.bemos.schooldiary.domain.models

data class User(
    val surname: String,
    val name: String,
    val patronymic: String,
    val email: String
)