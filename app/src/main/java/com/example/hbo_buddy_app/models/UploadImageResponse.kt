package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class UploadImageResponse(
    val `data`: Data,
    val status: Int,
    val success: Boolean
)