package com.example.hbo_buddy_app.select_buddy.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.LoginModel
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


class BuddyProfileRepository @Inject constructor(
    var retrofitService : RetroFitService)
{

    fun getBuddyProfileLiveData(): MutableLiveData<ArrayList<CoachProfile>> {
        val mutableLiveData = MutableLiveData<ArrayList<CoachProfile>>()


        retrofitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String> {
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

                retrofitService2.getAllCoachProfiles().enqueue(object: Callback<ArrayList<CoachProfile>>{
                    override fun onFailure(call: Call<ArrayList<CoachProfile>>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<ArrayList<CoachProfile>>, response: Response<ArrayList<CoachProfile>>) {
                        if (response.isSuccessful){
                            val arr = response.body()!!

                            arr.forEach{
                                if (it.coach.workload == 0 || it.student.firstName == "mock"){
                                    arr.remove(it)
                                }
                            }

                            mutableLiveData.value = arr


                        }
                    }

                })

            }

        })

        return mutableLiveData
    }



    fun startPoll(){
        //var lastMessage: String
        //lastMessage = "";
    }

}