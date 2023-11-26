package com.example.finalproject_chilicare.data.response

import android.os.Parcel
import android.os.Parcelable

data class CardResponse(
    var dataTitle: String,
    var dataSubtitle: String,
    var dataDescription: String,
    var dataDurasibaca: String,
    var dataImage: Int,
    // untuk keperluan di detail Article yaa
    var dataImageDetails: Int,
    var dataTanggalDetails: String,
    var dataWaktuDetails: String,
    var dataTitleDetails: String,
    var dataSubtitleDetails: String,
    var dataWebViewDetails: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dataTitle)
        parcel.writeString(dataSubtitle)
        parcel.writeString(dataDescription)
        parcel.writeString(dataDurasibaca)
        parcel.writeInt(dataImage)
        parcel.writeInt(dataImageDetails)
        parcel.writeString(dataTanggalDetails)
        parcel.writeString(dataWaktuDetails)
        parcel.writeString(dataTitleDetails)
        parcel.writeString(dataSubtitleDetails)
        parcel.writeString(dataWebViewDetails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardResponse> {
        override fun createFromParcel(parcel: Parcel): CardResponse {
            return CardResponse(parcel)
        }

        override fun newArray(size: Int): Array<CardResponse?> {
            return arrayOfNulls(size)
        }
    }
}