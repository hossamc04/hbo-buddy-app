package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class Image(
    val extension: String,
    val filename: String,
    val mime: String,
    val name: String,
    val size: Int,
    val url: String
)