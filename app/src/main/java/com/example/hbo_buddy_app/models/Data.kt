package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("delete_url")
    val deleteUrl: String,
    @SerializedName("display_url")
    val displayUrl: String,
    val id: String,
    val image: Image,
    val thumb: Thumb,
    val time: String,
    val title: String,
    val url: String,
    @SerializedName("url_viewer")
    val urlViewer: String
)