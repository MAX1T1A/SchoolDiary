package com.bemos.schooldiary.domain.models

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class Lesson(
    @SerializedName("group_id")
    val groupId: Group,
    @SerializedName("teacher_id")
    val teacherId: User,
    @SerializedName("subject_id")
    val subjectId: Subject,
    val date: Date,
    @SerializedName("start_time")
    val startTime: Timestamp,
    @SerializedName("end_time")
    val endTime: Timestamp
)