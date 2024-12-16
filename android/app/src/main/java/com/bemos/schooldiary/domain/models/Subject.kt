package com.bemos.schooldiary.domain.models

import com.google.gson.annotations.SerializedName

data class Subject(
    val name: String,
    val description: String,
    @SerializedName("color_hex")
    val colorHex: String
)