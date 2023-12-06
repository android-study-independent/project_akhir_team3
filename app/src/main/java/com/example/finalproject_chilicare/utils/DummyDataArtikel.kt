package com.example.finalproject_chilicare.utils

import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.article.TabResponse

object DummyDataArtikel {

    val tabTitleList = arrayOf("Menanam", "Bibit", "Hama", "Tanah", "Pestisida")

    val cardTitleList = arrayOf(
        "Menanam",
        "Bibit",
        "Hama",
        "Pestisida",
        "Menanam",
        "Menanam",
        "Petisida",
        "Petisida",
        "Petisida",
        "Petisida"
    )

    val cardSubtitleList = arrayOf(
        "Mudah Menanam Cabe Agar Cepat Berbuah",
        "Tips Memilih Bibit Cabai Yang Berkualitas",
        "Cegah Hama Pada Tanaman Cabai",
        "Membuat Pestisida Alami Tanaman Cabai",
        "Cara Mudah Menanam Cabai di Pot",
        "Teknik Budidaya Cabai Rawit",
        "Teknik Budidaya Cabai Rawit",
        "Membuat pestisida alami tanaman cabai",
        "Membuat pestisida alami tanaman cabai",
        "Membuat pestisida alami tanaman cabai"
    )

    val cardDescriptionList = arrayOf(
        "Semakin banyak kegiatan seru di tengah kesibukan, salah satunya bertani",
        "Bibit yang berkualitas memiliki beberapa ciri-ciri yang bisa chili-cares amati",
        "Hama dapat menyebabkan gagal panen pada tanaman cabai Anda.",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
        "Cabai rawit atau cabai kecil (Capsicum frutescens) termasuk dalamfamili Solanaceae dan merupakan tanaman berumur panjang (menahun)",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
        "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi"
    )


    val cardDurasibacaList = arrayOf(
    "3 menit baca",
    "5 menit baca",
    "4 menit baca",
    "5 menit baca",
    "5 menit baca",
    "10 menit dibaca",
    "10 menit dibaca",
    "10 menit dibaca",
    "10 menit dibaca",
    "10 menit dibaca"
    )

    val cardImageList = arrayOf(
    R.drawable.gambar_1,
    R.drawable.gambar_2,
    R.drawable.gambar_3,
    R.drawable.gambar_4,
    R.drawable.gambar_5,
    R.drawable.gambar_6,
    R.drawable.gambar_7,
    R.drawable.gambar_8,
    R.drawable.gambar_9,
    R.drawable.gambar_10
    )

    val dataImageDetails = arrayOf(
    R.drawable.gambar_1,
    R.drawable.gambar_2,
    R.drawable.gambar_3,
    R.drawable.gambar_4,
    R.drawable.gambar_5,
    R.drawable.gambar_6,
    R.drawable.gambar_7,
    R.drawable.gambar_8,
    R.drawable.gambar_9,
    R.drawable.gambar_10
    )

    val dataTanggalDetails = arrayOf(
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021",
    "19 Januari 2021"
    )

    val dataWaktuDetails = arrayOf(
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB",
    "19:00 WIB"
    )

    val dataTitleDetails = arrayOf(
    "Mudah Menanam Cabe Agar Cepat Berbuah",
    "Tips Memilih Bibit Cabai Yang Berkualitas",
    "Cegah Hama Pada Tanaman Cabai",
    "Membuat Pestisida Alami Tanaman Cabai",
    "Cara Mudah Menanam Cabai di Pot",
    "Teknik Budidaya Cabai Rawit",
    "Teknik Budidaya Cabai Rawit",
    "Membuat pestisida alami tanaman cabai",
    "Membuat pestisida alami tanaman cabai",
    "Membuat pestisida alami tanaman cabai"
    )

    val dataSubtitleDetails = arrayOf(
    "Menanam",
    "Bibit",
    "Hama",
    "Pestisida",
    "Menanam",
    "Menanam",
    "Petisida",
    "Petisida",
    "Petisida",
    "Petisida"
    )

    val dataWebViewDetails = Array(cardImageList.size) { "" }


    fun getDummyTabResponses(): List<TabResponse> {
        val tabResponses = mutableListOf<TabResponse>()
        for (title in tabTitleList) {
            tabResponses.add(TabResponse(title))
        }
        return tabResponses
    }

//    fun getDummyCardResponses(): List<CardArtikelResponse> {
//        val cardArtikelRespons = mutableListOf<CardArtikelResponse>()
//        for (i in cardImageList.indices) {
//            if (i < cardTitleList.size &&
//                i < cardSubtitleList.size &&
//                i < cardDescriptionList.size &&
//                i < cardDurasibacaList.size &&
//                i < dataImageDetails.size &&
//                i < dataTanggalDetails.size &&
//                i < dataWaktuDetails.size &&
//                i < dataTitleDetails.size &&
//                i < dataSubtitleDetails.size &&
//                i < dataWebViewDetails.size
//            ) {
//                val card = CardArtikelResponse(
//                    cardTitleList[i],
//                    cardSubtitleList[i],
//                    cardDescriptionList[i],
//                    cardDurasibacaList[i],
//                    cardImageList[i],
//                    dataImageDetails[i],
//                    dataTanggalDetails[i],
//                    dataWaktuDetails[i],
//                    dataTitleDetails[i],
//                    dataSubtitleDetails[i],
//                    dataWebViewDetails[i]
//                )
//                cardArtikelRespons.add(card)
//            } else {
//                Log.e(
//                    "DummyDataUtil",
//                    "Index out of bounds: i=$i, " +
//                            "cardTitleList.size=${cardTitleList.size}, " +
//                            "cardSubtitleList.size=${cardSubtitleList.size}, " +
//                            "cardDescriptionList.size=${cardDescriptionList.size}, " +
//                            "cardDurasibacaList.size=${cardDurasibacaList.size}, " +
//                            "dataImageDetails.size=${dataImageDetails.size}, " +
//                            "dataTanggalDetails.size=${dataTanggalDetails.size}, " +
//                            "dataWaktuDetails.size=${dataWaktuDetails.size}, " +
//                            "dataTitleDetails.size=${dataTitleDetails.size}, " +
//                            "dataSubtitleDetails.size=${dataSubtitleDetails.size}, " +
//                            "dataWebViewDetails.size=${dataWebViewDetails.size}"
//                )
//            }
//        }
//        return cardArtikelRespons
//    }


}