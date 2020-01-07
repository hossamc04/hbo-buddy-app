package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class Error(
    val code: Int,
    val context: String,
    val message: String
)