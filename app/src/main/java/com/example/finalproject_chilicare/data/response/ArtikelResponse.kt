package com.example.finalproject_chilicare.data.response

import com.example.finalproject_chilicare.dataclass.DataAllArtikel

data class ArtikelResponse(
    val data : List<DataAllArtikel>,
    val message : String,
    val status : String
)
