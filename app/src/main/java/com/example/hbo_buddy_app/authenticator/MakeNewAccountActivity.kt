package com.example.hbo_buddy_app.authenticator

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.hbo_buddy_app.MainActivity
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.retrofit.RetroFitService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class MakeNewAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_new_account)

        val button: Button = findViewById(R.id.submit_new_button)
        val leerlingnummer: EditText = findViewById(R.id.nieuw_acc_leerlingnummer)
        val wachtwoord: EditText = findViewById(R.id.niew_account_password)

        fun addAccount(string: String) {

            val am = AccountManager.get(this)
            val acc = Account(leerlingnummer.text.toString(),"inholland_buddy_app")
            val extraData = Bundle()
            extraData.putString("student_type", "4")
            am.addAccountExplicitly(acc, wachtwoord.text.toString(), extraData)
            val intent = Intent(this, MainActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }



        button.setOnClickListener {
            Log.d("clicked", "clicked")

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

                        val retrofitService2 = Retrofit
                            .Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient2)
                            .build()
                            .create(RetroFitService::class.java)

                        retrofitService2.registerStudent(
                            RegisterModel(
                                leerlingnummer.text.toString(),
                                response.body()!!,
                                4
                            )
                        ).enqueue(object : Callback<String> {
                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.d("ffs", "${t.message}")
                            }

                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                if (response.isSuccessful && response.code() == 201){
                                    retrofitService2.addEmptyProfile(TutorantProfile(
                                      Student("","", "mock","" ,"" ,"" ,leerlingnummer.text.toString() ,"",2,""),
                                        Tutorant(leerlingnummer.text.toString())
                                    )).enqueue(object : Callback<String>{
                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            Log.d("responsecode", "${response.code()}")

                                        }

                                        override fun onResponse(call: Call<String>, response: Response<String>) {
                                            if (response.isSuccessful()){
                                                Log.d("responsecode", "${response.code()}")
                                                addAccount(leerlingnummer.text.toString())
                                            }
                                        }
                                    })
                                }

                                else if (response.isSuccessful && response.code() != 201){
                                    Log.d("error", "${response.code()}")

                                }
                            }


                        })
                    }
                }
            })
        }
    }
}


/*
                                retrofitService2.addEmptyProfile(
                                TutorantProfile(
                                    Tutorant(leerlingnummer.text.toString()),
                                    Student(
                                        "bb",
                                        "bb",
                                        "bb",
                                        "bb",
                                        "123",
                                        "",
                                        leerlingnummer.text.toString(),
                                        "",
                                        1,
                                        ""
                                    )
                                )
                                )
                            .enqueue(object : Callback<String> {
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d("", "${t.message}")
                                }

                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    Log.d("aaa", "")
                                    Log.d("ffs", "${response.code()}")
                                    if (response.code() == 201) {
                                        startNew()
                                    }

                                }
                            })
                    }
                })
            }
        }
    })
}*/
