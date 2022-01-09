package com.vodafone.technicalassessment.presentation.ui.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.vodafone.technicalassessment.presentation.components.loadPicture
import com.vodafone.technicalassessment.presentation.ui.theme.TechnicalAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TechnicalAssessmentTheme {
                val scaffoldState = rememberScaffoldState()
                val palette = remember { viewModel.palette }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Surface(
                        color = Color(viewModel.item?.rgb ?: 0x000000)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(0.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .requiredHeight(350.dp),
                                shape = RoundedCornerShape(0.dp),
                                elevation = 4.dp,
                            ) {
                                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                    val imageRefs = createRef()

                                    // image
                                    loadPicture(url = viewModel.item?.download_url!!)?.let {
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
                                            painter = rememberImagePainter(data = it),
                                            contentDescription = "${viewModel.item?.author} - image",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}