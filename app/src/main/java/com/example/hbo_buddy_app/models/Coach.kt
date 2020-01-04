package com.example.hbo_buddy_app.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Coach(
    val studentID: Int,
    val workload: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(studentID)
        parcel.writeInt(workload)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coach> {
        override fun createFromParcel(parcel: Parcel): Coach {
            return Coach(parcel)
        }

        override fun newArray(size: Int): Array<Coach?> {
            return arrayOfNulls(size)
        }
    }
}