package com.example.hbo_buddy_app.retrofit

import com.example.hbo_buddy_app.models.DefaultResponse
import com.example.hbo_buddy_app.models.ErrorDebug
import com.example.hbo_buddy_app.models.UploadImageResponse

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import javax.security.auth.callback.Callback

interface RetrofitImageBb {

    @FormUrlEncoded
    @POST("/1/upload")
    fun uploadImage(@Field("image")image: String, @Query("key") key : String) : Call<UploadImageResponse>
}