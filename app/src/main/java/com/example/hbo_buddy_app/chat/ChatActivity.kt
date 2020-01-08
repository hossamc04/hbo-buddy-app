package com.example.hbo_buddy_app.chat

import android.accounts.AccountManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.dagger.activity_components.DaggerChatActivityComponent
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.LoginModel
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_chat.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


import javax.inject.Inject

class ChatActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ChatViewModel
    val adapter = ChatRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        //viewmodel
        DaggerChatActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(ChatViewModel::class.java)


        //trashy beta version code

        val am = AccountManager.get(this)
        val accounts = am.getAccountsByType("inholland_buddy_app")

            val studentId = accounts[0].name
            val accountType : Int = am.getUserData(accounts[0], "student_type").toInt()

            val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

            val okHttpClient: OkHttpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build()


            val retroFitService = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetroFitService::class.java)

        retroFitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("AdminToken", "${response.body()}")
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

                //observeMessages()

                if(accountType == 4){
                    retrofitService2.getCoachTutorantConnection(studentId).enqueue(object : Callback<CoachTutorantConnection>{
                        override fun onFailure(call: Call<CoachTutorantConnection>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<CoachTutorantConnection>, response: Response<CoachTutorantConnection>) {
                            if (response.isSuccessful && response.code() == 200){

                                Log.d("studentId", "${studentId}")
                                Log.d("coachId", "${response.body()!!.studentIDCoach}")
                                observeMessages(studentId,response.body()!!.studentIDCoach)

                                chat_send_button.setOnClickListener {
                                    sendMessage(studentId, response.body()!!.studentIDCoach)
                                }


                            }
                        }

                    })
                }
                else if(accountType == 3){
                    val tutorant = intent.extras!!.get("TUTORANT").toString()
                    retrofitService2.getStudent(tutorant).enqueue(object : Callback<Student>{
                        override fun onFailure(call: Call<Student>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<Student>, response: Response<Student>) {
                            if (response.isSuccessful && response.code() == 200){
                                setTitle(response.body()!!.firstName)

                            }
                        }

                    })


                    Log.d("studentId", "${studentId}")
                    Log.d("coachId", "${tutorant}")
                    val student =
                    observeMessages(studentId,tutorant)

                    chat_send_button.setOnClickListener {
                        sendMessage(studentId, tutorant)
                    }



                }
            }

        }
    )




        //setuprecyclerview
        chat_recycler_view.layoutManager = LinearLayoutManager(this)
        chat_recycler_view.adapter = adapter



    }


    fun sendMessage(studentId: String, coachId: String){
        viewModel.onSendMessage(studentId, coachId, chat_textedit.text.toString())
    }


    fun observeMessages(studentId : String, coachId: String){
        viewModel.getAllMesages(coachId, studentId).observe(this, Observer {
            adapter.addItems(it)

        })
    }
}
