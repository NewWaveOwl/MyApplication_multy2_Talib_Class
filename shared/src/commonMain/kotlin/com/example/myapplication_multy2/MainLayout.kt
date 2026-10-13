package com.example.myapplication_multy2.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication_multy2.ScreenKey
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.fab_home
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainLayout(
    screenTitle: String,
    currentScreen: ScreenKey?,
    canGoBack: Boolean,
    onBack: () -> Unit,
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onContact: () -> Unit,
    onMySimple: () -> Unit,
    onMySimple2: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { SharedTopBar(screenTitle, canGoBack, onBack) },
        bottomBar = {
            SharedBottomBar(currentScreen, onHome, onAbout, onContact, onMySimple, onMySimple2)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onHome) {
                Text(stringResource(Res.string.fab_home))
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            content()
        }
    }
}
