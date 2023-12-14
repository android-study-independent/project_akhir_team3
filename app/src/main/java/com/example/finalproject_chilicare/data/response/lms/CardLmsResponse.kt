package com.example.finalproject_chilicare.data.response.lms

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CardLmsResponse(
    @SerializedName("judul")
    val judul : String?,
    @SerializedName("id")
    val id : String?,
    @SerializedName("desc")
    val desc : String?,
    @SerializedName("tanggal")
    val tanggal : String?,
    @SerializedName("status")
    val status : String?,
    @SerializedName("learning_time")
    val learningTime : String?,
    @SerializedName("total_materi")
    val totalMateri : Int?,
    @SerializedName("covers")
    val covers : String?,
    @SerializedName("listing_materi")
    val listMateri : List<ListMateriLMS>?

): Parcelable  {
    constructor(parcel : Parcel) : this (
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(ListMateriLMS.CREATOR)

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(id)
        parcel.writeString(desc)
        parcel.writeString(tanggal)
        parcel.writeString(status)
        parcel.writeString(learningTime)
        parcel.writeValue(totalMateri)
        parcel.writeString(covers)
        parcel.writeTypedList(listMateri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardLmsResponse> {
        override fun createFromParcel(parcel: Parcel): CardLmsResponse {
            return CardLmsResponse(parcel)
        }

        override fun newArray(size: Int): Array<CardLmsResponse?> {
            return arrayOfNulls(size)
        }
    }
}
