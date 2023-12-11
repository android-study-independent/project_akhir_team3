package com.example.finalproject_chilicare.data.response.lms

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ModulMateri(
    @SerializedName("judul")
    val judul : String?,
    @SerializedName("desc")
    val desc : String?,
    @SerializedName("tanggal")
    val tanggal : String?,
    @SerializedName("status")
    val status : String?,
    @SerializedName("learning_time")
    val learningTime : String?,
    @SerializedName("total_materi")
    val totalMateri : Int,
    @SerializedName("covers")
    val coverPath : String?,

) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
        // DETAILS ARTIKEL
    )

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(desc)
        parcel.writeString(tanggal)
        parcel.writeString(status)
        parcel.writeString(learningTime)
        parcel.writeInt(totalMateri)
        parcel.writeString(coverPath)

    }

    companion object CREATOR : Parcelable.Creator<ModulMateri> {
        override fun createFromParcel(parcel: Parcel): ModulMateri {
            return ModulMateri(parcel)
        }

        override fun newArray(size: Int): Array<ModulMateri?> {
            return arrayOfNulls(size)
        }
    }
}
