package com.example.hbo_buddy_app.authenticator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.models.LoginModel
import com.example.hbo_buddy_app.retrofit.RetroFitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(val retroFitService: RetroFitService) {

    val tokenLiveData = MutableLiveData<String?>()

    fun login(username: String?, password: String?): MutableLiveData<String?> {
        if (username != null && password != null) {


            retroFitService.login(LoginModel(username, password)).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        tokenLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("error", "${t.message}")
                }
            })
        }

        else{
            tokenLiveData.value = null
        }

        return tokenLiveData
    }

}