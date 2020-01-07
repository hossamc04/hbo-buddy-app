package com.example.hbo_buddy_app.retrofit


import com.example.hbo_buddy_app.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetroFitService {
  @POST("/api/auth/login")
  fun login(@Body login : LoginModel): Call<String>
  @GET("/api/profile/coach")
  fun getAllCoachProfiles(): Call<ArrayList<CoachProfile>>

  @Headers("Content-Type: application/json")
  @POST("/api/auth/login")
  fun loginStudent(@Body body: LoginModel): Call<UserAuth>

  @POST("/api/profile/tutorant")
  fun addEmptyProfile(@Body body: TutorantProfile) : Call<String>

  @GET("/api/student/{studentId}")
  fun getProfileInformation(@Path("studentId")studentId: String)  : Call<Student>

   @GET("/api/profile/tutorant/{studentId}")
   fun getTutorantProfileById(@Path("studentId")studentId: String)  : Call<TutorantProfile>

   @GET("/api/profile/coach/{studentId}")
   fun getcoachrofileById(@Path("studentId")studentId: String)  : Call<CoachProfile>

    @PUT("/api/student/{studentId}")
    fun editProfile(@Path("studentId")studentId : String, @Body body: Student) : Call<ResponseBody>

    @POST("/api/coachTutorant/tutorant/{studentId}")
    fun makeCoachTutorantConnection(@Path("studentId")studentId: String, @Body body: CoachTutorantConnection) : Call<ResponseBody>

    @GET("/api/coachTutorant/tutorant/{studentId}")
    fun getCoachTutorantConnection(@Path("studentId")studentId: String ) : Call<CoachTutorantConnection>

    @GET("/api/messages/{senderId}/{recieverId}")
    fun getMessages(@Path("senderId")senderId: String, @Path("recieverId")recieverId : String) : Call<ArrayList<Message>>

    @POST("/api/message/")
    fun sendMessage(@Body body : MessageSend) : Call<ResponseBody>

















    @Headers("Content-Type: application/json")
  @POST("/api/auth/register")
  fun registerStudent(@Body body: RegisterModel): Call<String>


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