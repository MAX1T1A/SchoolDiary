package com.bemos.schooldiary.domain.models

import com.google.gson.annotations.SerializedName

data class Group(
    val name: String,
    @SerializedName("teacher_id")
    val teacher: User
)