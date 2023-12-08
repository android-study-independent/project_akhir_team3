package com.example.finalproject_chilicare.data.models

data class AllForumResponse (
    val status : String,
    val message: String,
    val allForumResponse : List<AllForumItem>

)