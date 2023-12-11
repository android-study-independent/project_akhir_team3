package com.example.finalproject_chilicare.data.response.forum

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
    val id_komentar: Int?,
    val id_user: Int?,
    val name: String?,
    val komentar: String?,
    val createdAt: String?,
    val id_parent_comment: Int?,
    val jumlahKomentar: Int?
)
