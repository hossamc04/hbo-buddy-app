package com.example.hbo_buddy_app.authenticator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.LoginModel
import com.example.hbo_buddy_app.models.TutorantProfile
import com.example.hbo_buddy_app.retrofit.RetroFitService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginRepository @Inject constructor(val retroFitService: RetroFitService) {

    val tokenLiveData = MutableLiveData<String?>()

    fun login(username: String?, password: String?): MutableLiveData<String?> {
        if (username != null && password != null) {

            retroFitService.login(LoginModel("581433", "test")).enqueue(object :  Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

                    val okHttpClient2 = OkHttpClient()
                        .newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(TokenHeaderInterceptor(response.body()!!))
                        .writeTimeout(60, TimeUnit.SECONDS).build()

                    val retrofitService2 = Retrofit
                        .Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient2)
                        .build()
                        .create(RetroFitService::class.java)

                    retrofitService2.getTutorantProfileById(username).enqueue(object: Callback<TutorantProfile>{
                        override fun onFailure(call: Call<TutorantProfile>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<TutorantProfile>, response: Response<TutorantProfile>) {
                            if (response.isSuccessful && response.code() == 200){
                                Log.d("wel/niet", "${response.code()}")
                                tokenLiveData.value = "4"
                            }

                            else if (response.code() == 404 || response.code() == 500){
                                retrofitService2.getCoachProfileById(username).enqueue(object: Callback<CoachProfile>{
                                    override fun onFailure(call: Call<CoachProfile>, t: Throwable) {

                                    }

                                    override fun onResponse(call: Call<CoachProfile>, response: Response<CoachProfile>) {
                                        if (response.isSuccessful && response.code() == 200){
                                            tokenLiveData.value = "3"
                                        }
                                        else{
                                            tokenLiveData.value = null
                                        }
                                    }

                                })
                            }
                        }

                    })

                }

            })



        }

        else{
            //tokenLiveData.value = null
        }

        return tokenLiveData
    }

}