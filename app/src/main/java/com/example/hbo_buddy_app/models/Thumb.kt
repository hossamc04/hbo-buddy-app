package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class Thumb(
    val extension: String,
    val filename: String,
    val mime: String,
    val name: String,
    val size: String,
    val url: String
)