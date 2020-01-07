package com.example.hbo_buddy_app.select_buddy

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.select_buddy.data.BuddyProfileRepository
import javax.inject.Inject

class SelectBuddyViewModel @Inject constructor(
    private val repository : BuddyProfileRepository): ViewModel()  {

    fun getAllCoaches(): LiveData<ArrayList<CoachProfile>> {
        return repository.getBuddyProfileLiveData()
    }


    fun onBuddySelect(loginId: String, CoachId : String, studentPassword: String){
        repository.makeConnection(loginId, CoachId, studentPassword)
    }

}