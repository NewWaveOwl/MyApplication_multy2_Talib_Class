package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_go_about
import myapplication_multy2.shared.generated.resources.btn_go_contact
import myapplication_multy2.shared.generated.resources.btn_go_viewmodel
import myapplication_multy2.shared.generated.resources.title_flow
import myapplication_multy2.shared.generated.resources.title_previews
import myapplication_multy2.shared.generated.resources.viewmodel_demo_number
import org.jetbrains.compose.resources.stringResource

/** Navigation menu and the two independently keyed demos from slide 31. */
@Composable
fun MainScreen(
    onGoAbout: () -> Unit,
    onGoContact: () -> Unit,
    onGoViewModel: () -> Unit,
    onGoFlow: () -> Unit,
    onGoPreviews: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onGoFlow) { Text(stringResource(Res.string.title_flow)) }
        Button(onClick = onGoPreviews) { Text(stringResource(Res.string.title_previews)) }
        Button(onClick = onGoViewModel) { Text(stringResource(Res.string.btn_go_viewmodel)) }
        Button(onClick = onGoAbout) { Text(stringResource(Res.string.btn_go_about)) }
        Button(onClick = onGoContact) { Text(stringResource(Res.string.btn_go_contact)) }
        HorizontalDivider()
        Text(stringResource(Res.string.viewmodel_demo_number, 1))
        MySimpleScreen2a(key = 1)
        HorizontalDivider()
        Text(stringResource(Res.string.viewmodel_demo_number, 2))
        MySimpleScreen2a(key = 2)
    }
}
