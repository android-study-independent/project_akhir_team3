package com.example.finalproject_chilicare.data.models

data class CreateForumResponse(
    val error: Boolean,
    val forum: ForumAddItem,
    val message: String
)