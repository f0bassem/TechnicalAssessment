package com.vodafone.technicalassessment.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.vodafone.technicalassessment.presentation.ui.theme.TransparentBlack

@Composable
fun ItemView(author: String, image: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .requiredHeight(250.dp),
        shape = RoundedCornerShape(26.dp),
        elevation = 4.dp,
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (imageRefs, textRefs) = createRefs()

            // image
            Image(
                modifier = Modifier
                    .constrainAs(imageRefs) {
                        start.linkTo(anchor = parent.start)
                        end.linkTo(anchor = parent.end)
                        top.linkTo(anchor = parent.top)
                        bottom.linkTo(anchor = parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints

                    },
                painter = rememberImagePainter(data = image),
                contentDescription = "$author - image",
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textRefs) {
                        start.linkTo(anchor = parent.start, margin = 0.dp)
                        end.linkTo(anchor = parent.end, margin = 0.dp)
                        bottom.linkTo(anchor = imageRefs.bottom)
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
                    text = author,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}