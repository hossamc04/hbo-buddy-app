package com.example.hbo_buddy_app.retrofit


import com.example.hbo_buddy_app.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetroFitService {
  @POST("auth/login")
  fun login(@Body login : LoginModel): Call<String>
  @GET("/api/profile/coach")
  fun getAllCoachProfiles(): Call<ArrayList<CoachProfile>>

  @Headers("Content-Type: application/json")
  @POST("/api/auth/login")
  fun loginStudent(@Body body: LoginModel): Call<UserAuth>



  @Headers("Content-Type: application/json")
  @POST("/api/auth/register")
  fun registerStudent(@Body body: RegisterModel): Call<DefaultResponse>


  //    @FormUrlEncoded
//    @POST("/api/auth/register")
//    fun registerStudent(@Field("studentID") studentID: Int, @Field("password") password: String, @Field("role") role: Int): Call<DefaultResponse>



/* @GET("Articles")
    fun getArticles() : Call<ArticleListAndNextId>
    @GET("Articles/{id}")
    fun getArticlesFromNextId(
        @Path("id")id:Int,
        @Query("count")amount:Int
    ) : Call<ArticleListAndNextId>
    @POST("Users/login")
    fun login(
        @Body credentials: Credentials
    ) : Call<Token>
    @POST("Users/register")
    fun register(
        @Body credentials: Credentials
    ) : Call<RegisterResponse>
 */

}