package com.example.hbo_buddy_app.models

import android.os.Parcel
import android.os.Parcelable


data class CoachProfile(
    val coach: Coach,
    val student: Student,
    var isVisable: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Coach::class.java.classLoader)!!,
        parcel.readParcelable(Student::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(coach, flags)
        parcel.writeParcelable(student, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoachProfile> {
        override fun createFromParcel(parcel: Parcel): CoachProfile {
            return CoachProfile(parcel)
        }

        override fun newArray(size: Int): Array<CoachProfile?> {
            return arrayOfNulls(size)
        }
    }
}