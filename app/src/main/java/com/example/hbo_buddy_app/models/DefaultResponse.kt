package com.example.hbo_buddy_app.models

import com.google.gson.annotations.SerializedName

class DefaultResponse(@SerializedName("Success")val success: Boolean, @SerializedName("Message")val message: String) {
}