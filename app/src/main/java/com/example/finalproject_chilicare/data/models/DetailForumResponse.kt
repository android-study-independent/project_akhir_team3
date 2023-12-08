package com.example.finalproject_chilicare.data.models

data class DetailForumResponse(
    val forum: DetailForumItem,
    val jumlahLike: Int,
    val komentars: List<ListComentarItem>,
    val likes: List<LikeItem>
)