package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication_multy2.layout.MainLayout
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.about_message
import myapplication_multy2.shared.generated.resources.btn_go_contact
import myapplication_multy2.shared.generated.resources.btn_go_main
import myapplication_multy2.shared.generated.resources.title_about
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutScreen(name: String) {
    val navigator = LocalNavigator.current

    MainLayout(screenTitle = stringResource(Res.string.title_about)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(Res.string.about_message, name))
            Button(onClick = { navigator.navigate(MainScreenKey) }) {
                Text(stringResource(Res.string.btn_go_main))
            }
            Button(onClick = { navigator.navigate(ContactScreenKey("Julie", "Paris")) }) {
                Text(stringResource(Res.string.btn_go_contact))
            }
        }
    }
}