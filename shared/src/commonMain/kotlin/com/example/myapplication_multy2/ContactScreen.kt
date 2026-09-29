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
import myapplication_multy2.shared.generated.resources.btn_go_about
import myapplication_multy2.shared.generated.resources.btn_go_main
import myapplication_multy2.shared.generated.resources.contact_message
import myapplication_multy2.shared.generated.resources.title_contact
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContactScreen(name: String, city: String) {
    val navigator = LocalNavigator.current

    MainLayout(screenTitle = stringResource(Res.string.title_contact)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(Res.string.contact_message, name, city))
            Button(onClick = { navigator.navigate(MainScreenKey) }) {
                Text(stringResource(Res.string.btn_go_main))
            }
            Button(onClick = { navigator.navigate(AboutScreenKey("Franz")) }) {
                Text(stringResource(Res.string.btn_go_about))
            }
        }
    }
}