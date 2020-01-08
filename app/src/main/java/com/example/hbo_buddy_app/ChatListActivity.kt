package com.example.hbo_buddy_app

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.models.*
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

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Chats")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val am = AccountManager.get(this)
        val accounts = am.getAccountsByType("inholland_buddy_app")
        val studentId = accounts[0].name
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

        retroFitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("AdminToken", "${response.body()}")
                    val okHttpClient2 = OkHttpClient()
                        .newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(TokenHeaderInterceptor(response.body()!!))
                        .writeTimeout(60, TimeUnit.SECONDS).build()

                    val api = Retrofit
                        .Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient2)
                        .build()
                        .create(RetroFitService::class.java)

                    val call = api.getCoachTutorantConnections(studentId)
                    call.enqueue(object : Callback<List<CoachTutorantConnection>> {
                        override fun onFailure(
                            call: Call<List<CoachTutorantConnection>>,
                            t: Throwable
                        ) {
                            //textView.text = t.message
                        }

                        override fun onResponse(
                            call: Call<List<CoachTutorantConnection>>,
                            response: Response<List<CoachTutorantConnection>>
                        ) {
                            if(response.code() == 404){
                                //let user know that no one has matched up with you yet
                                status.text = "You have yet to be matched up with!"
                                return
                            }

                            chatlist.layoutManager = LinearLayoutManager(this@ChatListActivity)
                            val buddies = response.body()

                            val clickInterface: ClickListener = object : ClickListener {
                                override fun onItemClick(position: Int) {
                                    val intent: Intent =
                                        Intent(this@ChatListActivity, ChatActivity::class.java)
                                    //intent.putExtra(INTENT_KEY_1, buddies!!.get(position))
                                    startActivity(intent)
                                }
                            }
                            val list: MutableList<Student> = mutableListOf<Student>()
                            buddies!!.forEach {
                                val tutorant = getTutorant(it.studentIDTutorant, okHttpClient2)
                                list.add(tutorant)
                            }

                            chatlist.adapter =
                                ChatListAdapter(this@ChatListActivity, list!!, clickInterface)
                            //chatlist.adapter.notifyDataSetChanged()

                        }
                    })
                }
            }
        })
    }

    private fun getTutorant(id: String, client: OkHttpClient): Student{
        val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"
        var tutorant = Student("", "","", "","","","","",0, "")

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
                    status.text = "Student doesnt exist!"
                    return
                }
                else if(response.code() == 200){
                    tutorant = response.body()!!
                }
            }
        })
        return tutorant
    }
}
