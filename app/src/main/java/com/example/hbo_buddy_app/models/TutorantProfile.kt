package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class TutorantProfile(
    val student: Student,
    val tutorant: Tutorant
)