package com.example.finalproject_chilicare.data.response.lms

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ModulResponse(
    @SerializedName("id")
    val id : Int,
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
    val totalMateri : Int?,
    @SerializedName("covers")
    val covers : String?,
    @SerializedName("listing_materi")
    val listMateri : List<ListMateriLMS>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
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
        parcel.writeValue(id)
        parcel.writeString(judul)
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

    companion object CREATOR : Parcelable.Creator<ModulResponse> {
        override fun createFromParcel(parcel: Parcel): ModulResponse {
            return ModulResponse(parcel)
        }

        override fun newArray(size: Int): Array<ModulResponse?> {
            return arrayOfNulls(size)
        }
    }
}
