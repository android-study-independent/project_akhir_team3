package com.example.finalproject_chilicare.data.response.lms

import com.google.gson.annotations.SerializedName

data class ModulMateri(
    @SerializedName("id")
    val id : Int ?,
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
    @SerializedName("total materi")
    val totalMateri : Int?,
    @SerializedName("covers")
    val coverPath : String,
)
