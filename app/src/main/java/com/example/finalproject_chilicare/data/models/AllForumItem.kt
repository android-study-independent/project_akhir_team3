package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllForumItem(
    @SerializedName("captions")
    val captions: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("forumId")
    val forumId: Int,

    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("image")
    val image: List<String>,

    @SerializedName("jumlahKomentar")
    val jumlahKomentar: Int,

    @SerializedName("jumlahLike")
    var jumlahLike: Int,

    @SerializedName("name_user")
    val nameUser: String
) : Serializable {

    var isLiked: Boolean = false

    // Metode untuk menangani aksi "like"
    fun like() {
        // Logika aksi "like" di sini
        // Misalnya, Anda dapat menambahkan logika untuk menyimpan status "liked"
        // atau melakukan aksi jaringan yang sesuai
        isLiked = true
    }

    // Metode untuk menangani aksi "unlike"
    fun unlike() {
        // Logika aksi "unlike" di sini
        // Misalnya, Anda dapat menambahkan logika untuk menghapus status "liked"
        // atau melakukan aksi jaringan yang sesuai
        isLiked = false
    }
}