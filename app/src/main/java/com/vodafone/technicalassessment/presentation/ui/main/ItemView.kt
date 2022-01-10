package com.vodafone.technicalassessment.presentation.ui.main

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.vodafone.technicalassessment.R
import com.vodafone.technicalassessment.presentation.base.AppConfiguration.Companion.AD_ID
import com.vodafone.technicalassessment.presentation.components.getRGB
import com.vodafone.technicalassessment.presentation.components.loadPicture
import com.vodafone.technicalassessment.presentation.ui.theme.TransparentBlack
import com.vodafone.technicalassessment.utils.Status

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ItemView(author: String, image: String, showAd: Boolean, onClick: (Int?) -> Unit) {

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var rgb: Int? = null

    val imageLoading = remember { mutableStateOf<Status?>(null) }
    if (imageLoading.value != Status.SUCCESS) {
        bitmap.value = loadPicture(url = image)
    }

    if (showAd)
        AdItem()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                if (rgb != null) {
                    onClick(rgb)
                } else {
                    imageLoading.value = Status.LOADING
                }
            },
        shape = RoundedCornerShape(26.dp),
        elevation = 4.dp,
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (imageRefs, refreshRefs, textRefs) = createRefs()

            bitmap.value = loadPicture(url = image)

            // image
            if (bitmap.value != null) {
                rgb = getRGB(bitmap = bitmap.value!!)
                imageLoading.value = Status.SUCCESS
                Image(
                    modifier = Modifier
                        .height(250.dp)
                        .constrainAs(imageRefs) {
                            start.linkTo(anchor = parent.start)
                            end.linkTo(anchor = parent.end)
                            top.linkTo(anchor = parent.top)
                            bottom.linkTo(anchor = parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints

                        },
                    painter = rememberImagePainter(data = bitmap.value!!),
                    contentDescription = "$author - image",
                    contentScale = ContentScale.Crop
                )
            } else {
                imageLoading.value = Status.FAILED
            }

            AnimatedVisibility(
                modifier = Modifier
                    //.size(48.dp)
                    .constrainAs(refreshRefs) {
                        start.linkTo(anchor = parent.start)
                        end.linkTo(anchor = parent.end)
                        top.linkTo(anchor = parent.top)
                        bottom.linkTo(anchor = parent.bottom)
                    },
                visible = imageLoading.value != Status.SUCCESS
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    imageVector = Icons.Filled.Image,
                    tint = Color.LightGray,
                    contentDescription = "refresh"
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textRefs) {
                        start.linkTo(anchor = parent.start, margin = 0.dp)
                        end.linkTo(anchor = parent.end, margin = 0.dp)
                        bottom.linkTo(anchor = parent.bottom)
                    },
                color = TransparentBlack
            ) {
                // text
                Text(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    text = "${stringResource(id = R.string.author)}: $author",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdItem() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ad view
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        AdSize.FULL_WIDTH
                    )
                    adUnitId = AD_ID
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->
                adView.loadAd(AdRequest.Builder().build())
            },
        )
    }
}