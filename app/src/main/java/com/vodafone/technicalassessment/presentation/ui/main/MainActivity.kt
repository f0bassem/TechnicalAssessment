package com.vodafone.technicalassessment.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.MobileAds
import com.vodafone.technicalassessment.R
import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import com.vodafone.technicalassessment.presentation.ui.details.DetailsActivity
import com.vodafone.technicalassessment.presentation.ui.theme.TechnicalAssessmentTheme
import com.vodafone.technicalassessment.utils.Constants.Companion.ITEM
import com.vodafone.technicalassessment.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
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

                        // empty view
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                modifier = Modifier,
                                visible = itemsList.size == 0 && apiStatus.value != Status.LOADING
                            ) {
                                // empty text
                                Text(
                                    modifier = Modifier,
                                    text = stringResource(id = R.string.no_data_available),
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.h6,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                        }
                        
                        // listing items
                        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyState) {
                            itemsIndexed(
                                items = itemsList,
                                key = { index, item -> item.id!! }) { index, item ->

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


    private fun navigate(item: ListItemDTO) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtras(bundleOf(ITEM to item))
        startActivity(intent)
    }

}