package com.vodafone.technicalassessment.presentation.ui.main

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.gms.ads.MobileAds
import com.vodafone.technicalassessment.domain.List
import com.vodafone.technicalassessment.presentation.ui.details.DetailsActivity
import com.vodafone.technicalassessment.presentation.ui.theme.TechnicalAssessmentTheme
import com.vodafone.technicalassessment.utils.Constants.Companion.ITEM
import com.vodafone.technicalassessment.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}

        setContent {
            val scaffoldState = rememberScaffoldState()
            val lazyState = rememberLazyListState()
            val apiStatus = remember { viewModel.apiStatus }
            val itemsList = remember { viewModel.itemsList }

            TechnicalAssessmentTheme(
                displayProgressBar = apiStatus.value
            ) {
                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Surface(color = MaterialTheme.colors.background) {
                        Box(modifier = Modifier.fillMaxSize()) {

                            // listing items
                            LazyColumn (state = lazyState){
                                itemsIndexed(
                                    items = itemsList,) { index, item ->

                                    // paginate
                                    if (index > itemsList.size - 2) {
                                        viewModel.page = viewModel.page + 1
                                        viewModel.onTriggerEvent(MainEvent.GetList)
                                    }

                                    // item view
                                    ItemView(
                                        author = item.author!!,
                                        image = item.download_url!!,
                                        showAd = index % 5 == 0 && index > 0,
                                        onClick = {
                                            item.rgb = it
                                            lifecycleScope.launch {
                                                navigate(item)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun navigate(item: List) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtras(bundleOf(ITEM to item))
        startActivity(intent)
    }

}