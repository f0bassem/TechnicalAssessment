package com.vodafone.technicalassessment.presentation.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vodafone.technicalassessment.utils.Constants.Companion.MAX_BITMAP_SIZE


@Composable
fun loadPicture(url: String): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Glide.with(LocalContext.current).asBitmap()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = if (resource.byteCount > MAX_BITMAP_SIZE) null else resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })

    return bitmap
}

@Composable
fun getRGB(bitmap: Bitmap): Int? {
    var rgb by remember { mutableStateOf<Int?>(null) }

    bitmap.let {
        Palette.from(it).generate { palette ->
            rgb = palette?.dominantSwatch?.rgb
        }
    }

    return rgb
}