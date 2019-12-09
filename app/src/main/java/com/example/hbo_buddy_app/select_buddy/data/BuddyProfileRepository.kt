package com.example.hbo_buddy_app.select_buddy.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.api.ApiService
import com.example.hbo_buddy_app.models.CoachProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class BuddyProfileRepository @Inject constructor(
    var retrofitService : ApiService)
{

    fun getBuddyProfileLiveData(): MutableLiveData<ArrayList<CoachProfile>> {
        val mutableLiveData = MutableLiveData<ArrayList<CoachProfile>>()

        retrofitService.getAllCoachProfiles().enqueue(object : Callback<ArrayList<CoachProfile>>{
            override fun onFailure(call: Call<ArrayList<CoachProfile>>, t: Throwable) {
                Log.d("CallFailure: ", t.message)
            }

            override fun onResponse(call: Call<ArrayList<CoachProfile>>, response: Response<ArrayList<CoachProfile>>){
                if (response.isSuccessful){
                    mutableLiveData.value = response.body()
                    Log.d("",response.body().toString())
                    //Log.d("Success, result: ", response.body().toString())
                }
            }
        })
        return mutableLiveData
    }



    fun startPoll(){
        //var lastMessage: String
        //lastMessage = "";
    }

}