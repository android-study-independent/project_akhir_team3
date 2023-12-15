package com.example.finalproject_chilicare.data.response.forum

import com.google.gson.annotations.SerializedName

data class ForumResponse(
    val forum: Forum?,
    val komentars: List<Komentar>?,
    val jumlahKomentar: Int?,
    val jumlahLike: Int?,
    val likes: List<Any>? // Ganti dengan tipe data yang sesuai jika tipe data likes diketahui
)

data class Forum(
    val id_user: Int?,
    val name: String?,
    val captions: String?,
    val image: List<String>?
)

data class Komentar(
    @SerializedName("id_komentar")
    val idKomentar: Int?,

    @SerializedName("id_user")
    val id_user: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("komentar")
    val komentar: String?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("id_parent_comment")
    val idParentComment: Int?,

    @SerializedName("jumlahKomentar")
    val jumlahKomentar: Int?
)