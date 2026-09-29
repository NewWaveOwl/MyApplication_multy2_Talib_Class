package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication_multy2.layout.MainLayout
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_go_about
import myapplication_multy2.shared.generated.resources.btn_go_contact
import myapplication_multy2.shared.generated.resources.main_greeting
import myapplication_multy2.shared.generated.resources.title_home
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen() {
    val navigator = LocalNavigator.current

    MainLayout(screenTitle = stringResource(Res.string.title_home)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { navigator.navigate(AboutScreenKey("Franz")) }) {
                Text(stringResource(Res.string.btn_go_about))
            }
            Button(onClick = { navigator.navigate(ContactScreenKey("Julie", "Paris")) }) {
                Text(stringResource(Res.string.btn_go_contact))
            }
        }
    }
}