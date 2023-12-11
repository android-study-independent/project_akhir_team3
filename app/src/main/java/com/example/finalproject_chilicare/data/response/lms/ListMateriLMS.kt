package com.example.finalproject_chilicare.data.response.lms

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListMateriLMS(
    @SerializedName("youtube")
    val youtube : String?,
    @SerializedName("long_desc")
    val longDesc : String?,
    @SerializedName("short_desc")
    val shortDesc : String?,
    @SerializedName("judul_materi")
    val judulMateri : String?,
): Parcelable {
    constructor( parcel : Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        //Materi Modul Lms
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(youtube)
        parcel.writeString(longDesc)
        parcel.writeString(shortDesc)
        parcel.writeString(judulMateri)
        //Materi Modul Lms
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListMateriLMS> {
        override fun createFromParcel(parcel: Parcel): ListMateriLMS {
            return ListMateriLMS(parcel)
        }

        override fun newArray(size: Int): Array<ListMateriLMS?> {
            return arrayOfNulls(size)
        }
    }


}
