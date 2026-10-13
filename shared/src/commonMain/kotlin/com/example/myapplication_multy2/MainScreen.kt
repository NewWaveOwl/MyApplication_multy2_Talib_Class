package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_go_about
import myapplication_multy2.shared.generated.resources.btn_go_contact
import myapplication_multy2.shared.generated.resources.btn_go_viewmodel
import org.jetbrains.compose.resources.stringResource

/** Stateless navigation menu; callbacks come from the app's navigation host. */
@Composable
fun MainScreen(onGoAbout: () -> Unit, onGoContact: () -> Unit, onGoViewModel: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onGoViewModel) { Text(stringResource(Res.string.btn_go_viewmodel)) }
        Button(onClick = onGoAbout) { Text(stringResource(Res.string.btn_go_about)) }
        Button(onClick = onGoContact) { Text(stringResource(Res.string.btn_go_contact)) }
    }
}
