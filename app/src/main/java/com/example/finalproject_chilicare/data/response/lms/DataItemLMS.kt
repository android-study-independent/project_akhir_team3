package com.example.finalproject_chilicare.adapter.lms

import com.google.gson.annotations.SerializedName

data class DataItemLMS(
    val desc: String,
    val id: Int,
    val judul: String,

    @SerializedName("learning_time")
    val learningTime: String,

//    @SerializedName("listing_materi")
//    val listingMateri: List<ListingMateri>,
//    val status: String,
//    val tanggal: String,

    @SerializedName("total_materi")
    val totalMateri: Int
)