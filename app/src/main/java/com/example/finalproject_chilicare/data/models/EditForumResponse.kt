package com.example.finalproject_chilicare.data.models

data class EditForumResponse(
    val Forum: ForumEditItem,
    val error: Boolean,
    val message: String
)