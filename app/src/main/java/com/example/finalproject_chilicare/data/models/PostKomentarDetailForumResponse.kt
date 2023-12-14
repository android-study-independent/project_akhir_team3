package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class PostKomentarDetailForumResponse(


    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("Balasan")
    val balasan: IsiComentarItem

)