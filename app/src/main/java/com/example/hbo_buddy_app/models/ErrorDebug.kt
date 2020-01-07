package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class ErrorDebug(
    val error: Error,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_txt")
    val statusTxt: String
)