package com.vodafone.technicalassessment.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vodafone.technicalassessment.presentation.ui.theme.TechnicalAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var scaffoldState: ScaffoldState

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            scaffoldState = rememberScaffoldState()

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
                            LazyColumn {
                                itemsIndexed(
                                    items = itemsList,
                                    key = { _, item -> item.id!! }) { index, item ->

                                    // paginate
                                    if (index > itemsList.size - 2) {
                                        viewModel.page = viewModel.page + 1
                                        viewModel.onTriggerEvent(MainEvent.GetList)
                                    }

                                    // item view
                                    ItemView(
                                        author = item.author!!,
                                        image = item.download_url!!
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