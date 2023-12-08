package com.example.finalproject_chilicare.data.models

data class ListComentarItem(
    val createdAt: String,
    val id_komentar: Int,
    val id_user: Int,
    val isi: String,
    val name: String
)