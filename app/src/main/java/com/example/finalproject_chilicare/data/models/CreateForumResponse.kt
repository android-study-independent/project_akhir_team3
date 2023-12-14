package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class CreateForumResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("forum")
    val forum: AddNewForumResponse,

    @SerializedName("message")
    val message: String,

    @SerializedName("Balasan")
    val balasan: List<itemKomentar>
)

data class itemKomentar(
    @SerializedName("id_komentar")
    val idKomentar: Int,

    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("komentar")
    val komentar: String

)