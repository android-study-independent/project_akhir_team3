package com.example.finalproject_chilicare.data.response.lms

import com.google.gson.annotations.SerializedName

data class DataModulResponse(
    @SerializedName("status")
    val status : String?,
    @SerializedName("message")
    val message : String?,
    @SerializedName("data")
    val data : List<ModulResponse>
)
