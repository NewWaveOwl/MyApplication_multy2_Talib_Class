package com.example.myapplication_multy2.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainLayout(
    screenTitle: String,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { SharedTopBar(screenTitle) },
        bottomBar = { SharedBottomBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            content()
        }
    }
}