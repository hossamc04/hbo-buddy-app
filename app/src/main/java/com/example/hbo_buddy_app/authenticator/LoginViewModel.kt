package com.example.hbo_buddy_app.authenticator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository)
    : ViewModel()  {

    var userName  : String?  = null
    var passWord  : String? = null


    fun login(): LiveData<String?> {
        return repository.login(userName, passWord)
        //Log.d("test " , "clicked" )

    }


}