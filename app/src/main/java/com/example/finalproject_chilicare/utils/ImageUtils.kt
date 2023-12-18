package com.example.finalproject_chilicare.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.squareup.picasso.Picasso

class ImageUtils {
//    val listDataItem = listForum[position]
//    val imageUrl = listDataItem.image[0]
//    val targetView = holder.imageForum
//    val targetWidth = 200
//    val targetHeight = 200

    companion object {

        fun loadImage(context: Context, imageUrl: String, targetImage: ImageView, widht: Int, height: Int) {
            val requeastOpntions = RequestOptions()
                .transform(MultiTransformation(CenterCrop(),RoundedCorners(16)))
                .override(widht,height)

            Glide.with(context)
                .load(imageUrl)
                .apply(requeastOpntions)
                .into(targetImage)
        }

        //load image dan resize image dengan glide
        //ImageUtils.loadImage(context,imageUrl,targetView,targetWidth,targetHeight)

    }

}