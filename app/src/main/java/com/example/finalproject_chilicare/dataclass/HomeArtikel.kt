package com.example.finalproject_chilicare.dataclass

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HomeArtikel(
    val category : String,
    val title : String,
    val decs : String,
    val readTime : String,
    val cover : Int,
)

//    @SerializedName("category")
//    val category: String?,
//    @SerializedName("title")
//    val title: String?,
//    @SerializedName("desc")
//    val desc: String?,
//    @SerializedName("content")
//    val content: String?,
//    @SerializedName("cover")
//    val coverPath: String?,
//    @SerializedName("read_time")
//    val readTime: String?,
//    @SerializedName("source")
//    val source: String?,

//)  : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()!!,
//        parcel.readString(),
//        parcel.readString(),
//        // DETAILS ARTIKEL
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeString(desc)
//        parcel.writeString(content)
//        parcel.writeString(coverPath)
//        parcel.writeString(category)
//        parcel.writeString(readTime)
//        parcel.writeString(source)
//        // DETAILS ARTIKEL
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<HomeArtikel> {
//        override fun createFromParcel(parcel: Parcel): HomeArtikel {
//            return HomeArtikel(parcel)
//        }
//
//        override fun newArray(size: Int): Array<HomeArtikel?> {
//            return arrayOfNulls(size)
//        }
//    }
