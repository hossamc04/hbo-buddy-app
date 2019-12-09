package com.example.hbo_buddy_app.api

import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.Student
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/profile/coach")
    fun getAllCoachProfiles(): Call<ArrayList<CoachProfile>>
}