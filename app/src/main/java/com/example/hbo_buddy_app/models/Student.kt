package com.example.hbo_buddy_app.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Student(
    val degree: String,
    val description: String,
    val firstName: String,
    val interests: String,
    val phoneNumber: String,
    val photo: String,
    val studentID: String,
    val study: String,
    val studyYear: Int,
    val surName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(degree)
        parcel.writeString(description)
        parcel.writeString(firstName)
        parcel.writeString(interests)
        parcel.writeString(phoneNumber)
        parcel.writeString(photo)
        parcel.writeString(studentID)
        parcel.writeString(study)
        parcel.writeInt(studyYear)
        parcel.writeString(surName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}