package com.example.myapplication_multy2.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication_multy2.ScreenKey

@Composable
fun MainLayout(
    screenTitle: String,
    currentScreen: ScreenKey?,
    canGoBack: Boolean,
    onBack: () -> Unit,
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onContact: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { SharedTopBar(screenTitle, canGoBack, onBack) },
        bottomBar = { SharedBottomBar(currentScreen, onHome, onAbout, onContact) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            content()
        }
    }
}
