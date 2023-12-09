package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddPostForumRequest {

    @SerializedName("captions")
    @Expose
    var captions: String? = null

    @SerializedName("images")
    @Expose
    var images: String? = null

}