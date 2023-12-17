package com.example.finalproject_chilicare.data.models

data class LikeItem(
    val id_user: Int,
    val liked_by: String
) {
    // Dengan asumsi kita akan menggunakan invoke untuk memberikan feedback aksi "like"
    operator fun invoke(listDataItem: AllForumItem): Boolean {
        // Logika aksi "like" di sini
        // Anda dapat mengembalikan nilai true atau false sesuai dengan kebutuhan
        // Misalnya, Anda dapat menyimpan status "liked" di dalam objek AllForumItem
        // atau melibatkan logika jaringan yang sesuai
        listDataItem.isLiked = true
        return true
    }
}