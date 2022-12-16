package com.example.individualproject.models

import android.os.Parcel
import android.os.Parcelable

class Doctor : Parcelable {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var speciality: String? = null
    var specialityId: Int = -1
    var phoneNumber: String? = null
    var password: String? = null
    var roomNumber: Int = -1

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        firstName = parcel.readString()
        lastName = parcel.readString()
        speciality = parcel.readString()
        specialityId = parcel.readInt()
        phoneNumber = parcel.readString()
        password = parcel.readString()
        roomNumber = parcel.readInt()
    }

    constructor(
        id: String?,
        firstName: String?,
        lastName: String?,
        speciality: String?,
        specialityId: Int,
        phoneNumber: String,
        password: String?,
        roomNumber: Int?
    ) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.speciality = speciality
        this.specialityId = specialityId
        this.phoneNumber = phoneNumber
        this.password = password
        if (roomNumber != null) {
            this.roomNumber = roomNumber
        }
    }

    constructor()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(id)
        p0?.writeString(firstName)
        p0?.writeString(lastName)
        p0?.writeString(speciality)
        p0?.writeInt(specialityId)
        p0?.writeString(phoneNumber)
        p0?.writeString(password)
        p0?.writeInt(roomNumber)
    }

    companion object CREATOR : Parcelable.Creator<Doctor> {
        override fun createFromParcel(parcel: Parcel): Doctor {
            return Doctor(parcel)
        }

        override fun newArray(size: Int): Array<Doctor?> {
            return arrayOfNulls(size)
        }
    }
}