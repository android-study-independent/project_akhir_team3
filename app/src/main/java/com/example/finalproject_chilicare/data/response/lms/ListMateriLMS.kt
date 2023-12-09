package com.example.finalproject_chilicare.data.response.lms

import com.google.gson.annotations.SerializedName

data class ListMateriLMS(
    @SerializedName("youtube")
    val youtube : String?,
    @SerializedName("long_desc")
    val longDesc : String?,
    @SerializedName("short_desc")
    val shortDesc : String?,
    @SerializedName("judul_materi")
    val judulMateri : String,

    val image : Int

)
