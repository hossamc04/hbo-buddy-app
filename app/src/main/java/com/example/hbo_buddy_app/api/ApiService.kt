package com.example.hbo_buddy_app.api

import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.models.DefaultResponse
import com.example.hbo_buddy_app.models.UserAuth
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface ApiService {
    @GET("/api/profile/coach")
    fun getAllCoachProfiles(): Call<ArrayList<CoachProfile>>

    @Headers("Content-Type: application/json")
    @POST("/api/auth/login")
    fun loginStudent(@Body body: LoginModel): Call<UserAuth>

//    @FormUrlEncoded
//    @POST("/api/auth/register")
//    fun registerStudent(@Field("studentID") studentID: Int, @Field("password") password: String, @Field("role") role: Int): Call<DefaultResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/auth/register")
    fun registerStudent(@Body body: RegisterModel): Call<DefaultResponse>
}