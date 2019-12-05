package com.example.hbo_buddy_app

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("buddy")
    fun getShit(): Call<Student>
}