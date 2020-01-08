package com.example.hbo_buddy_app

import android.accounts.AccountManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.LoginModel
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_chat_list.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StudentRepository @Inject constructor(
    var retrofitService : RetroFitService
) {
    fun getStudent(id: String, client: OkHttpClient): MutableLiveData<Student>{
        val mutableLiveData = MutableLiveData<Student>()
        val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

        val api = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RetroFitService::class.java)

        val call = api.getStudent(id)
        call.enqueue(object : Callback<Student> {
            override fun onFailure(
                call: Call<Student>,
                t: Throwable
            ) {
                //textView.text = t.message
            }

            override fun onResponse(
                call: Call<Student>,
                response: Response<Student>
            ) {
                if (response.code() == 404) {
                    //let user know that no one has matched up with you yet
                    return
                }
                else if(response.code() == 200){
                    mutableLiveData.value = response.body()
                }
            }
        })
        return mutableLiveData




















//        val mutableLiveData = MutableLiveData<CoachTutorantConnection>()
//
//        val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"
//
//        val okHttpClient: OkHttpClient = OkHttpClient()
//            .newBuilder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS).build()
//
//
//        val retroFitService = Retrofit
//            .Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(RetroFitService::class.java)
//
//        retroFitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    Log.d("AdminToken", "${response.body()}")
//                    val okHttpClient2 = OkHttpClient()
//                        .newBuilder()
//                        .connectTimeout(60, TimeUnit.SECONDS)
//                        .readTimeout(60, TimeUnit.SECONDS)
//                        .addInterceptor(TokenHeaderInterceptor(response.body()!!))
//                        .writeTimeout(60, TimeUnit.SECONDS).build()
//
//                    val api = Retrofit
//                        .Builder()
//                        .baseUrl(baseUrl)
//                        .addConverterFactory(ScalarsConverterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(okHttpClient2)
//                        .build()
//                        .create(RetroFitService::class.java)
//
//                    val call = api.getCoachTutorantConnections(id)
//                    call.enqueue(object : Callback<List<CoachTutorantConnection>> {
//                        override fun onFailure(
//                            call: Call<List<CoachTutorantConnection>>,
//                            t: Throwable
//                        ) {
//                            //textView.text = t.message
//                        }
//
//                        override fun onResponse(
//                            call: Call<List<CoachTutorantConnection>>,
//                            response: Response<List<CoachTutorantConnection>>
//                        ) {
//                            if (response.code() == 404) {
//                                //let user know that no one has matched up with you yet
//                                return
//                            }
//                            mutableLiveData.value = response.body()
//                        }
//                    })
//                }
//
//            }
//        })
//        return mutableLiveData
    }


}