package com.example.hbo_buddy_app.chat.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.models.LoginModel
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.MessageSend
import com.example.hbo_buddy_app.retrofit.RetroFitService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChatRepository @Inject constructor(
    var retrofitService : RetroFitService)
{

    val arrayList = ArrayList<Message>()


    fun getMessagesLiveData(coachID : String, studentId: String): MutableLiveData<ArrayList<Message>> {
        val mutableLiveData = MutableLiveData<ArrayList<Message>>()
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {

                Log.d("running", "running")

                retrofitService.login(LoginModel("581433", "test")).enqueue(object :
                    Callback<String> {
                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                        Log.d("error", "${t.message}")
                    }

                    //this arraylist contains everything fetched including duplicates


                    override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {

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

                        if (response.isSuccessful){
                            retrofitService2.getMessages(studentId,coachID).enqueue(object: Callback<ArrayList<Message>>{
                                override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onResponse(call: Call<ArrayList<Message>>, response: Response<ArrayList<Message>>) {
                                    arrayList.addAll(response.body()!!)

                                    retrofitService2.getMessages(coachID, studentId).enqueue(object: Callback<ArrayList<Message>>{
                                        override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onResponse(call: Call<ArrayList<Message>>, response: Response<ArrayList<Message>>) {
                                            arrayList.addAll(response.body()!!)
                                            mutableLiveData.value = arrayList
                                        }
                                    })
                                }

                            })


                        }
                    }
                })


                mainHandler.postDelayed(this, 1000)
            }
        })


        return mutableLiveData
    }

    fun sendMessage(sendmessage: MessageSend) {
        retrofitService.login(LoginModel("581433", "test")).enqueue(object :
            Callback<String> {
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.d("error", "${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {

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

                retrofitService2.sendMessage(sendmessage).enqueue(object : Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("error","ff")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Log.d("messageSend","${response.code()}  ${response.message()}")
                    }

                })
            }
        })
    }


}