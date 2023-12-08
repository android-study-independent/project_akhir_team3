package com.example.finalproject_chilicare.data.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class AllForumResponse (
    val status : String,
    val message : String,
    val allForumItem : List<AllForumItem>

)