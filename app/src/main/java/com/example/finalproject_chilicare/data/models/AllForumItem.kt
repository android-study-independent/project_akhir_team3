package com.example.finalproject_chilicare.data.models

data class AllForumItem(
    val captions: String,
    val createdAt: String,
    val forumId: Int,
    val id_user: Int,
    val image: List<String>,
    val jumlahKomentar: Int,
    val jumlahLike: Int,
    val name_user: String
)