package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class AddComentarResponse(
    @SerializedName("Balasan")
    val Balasan: IsiComentarItem,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String
)