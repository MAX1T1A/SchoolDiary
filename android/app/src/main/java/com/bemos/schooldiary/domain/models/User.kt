package com.bemos.schooldiary.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    val surname: String,
    val name: String,
    val patronymic: String,
    val email: String,
)

enum class Role {
    @SerializedName("student")
    STUDENT,

    @SerializedName("teacher")
    TEACHER,

    @SerializedName("admin")
    ADMIN
}