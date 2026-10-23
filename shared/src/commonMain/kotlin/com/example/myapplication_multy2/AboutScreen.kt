package com.example.myapplication_multy2
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.AboutViewModel
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.about_message
import myapplication_multy2.shared.generated.resources.btn_go_contact
import myapplication_multy2.shared.generated.resources.btn_go_main
import org.jetbrains.compose.resources.stringResource
import myapplication_multy2.shared.generated.resources.info_about

@Composable
fun AboutScreen(
    name: String,
    onGoMain: () -> Unit,
    onGoContact: () -> Unit,
    myViewModel: AboutViewModel = viewModel { AboutViewModel(name) }
) {
    AboutContent(myViewModel.person.name, onGoMain, onGoContact)
}

@Composable
fun AboutContent(name: String, onGoMain: () -> Unit, onGoContact: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ExerciseInfoButton(stringResource(Res.string.info_about))
        Text(stringResource(Res.string.about_message, name))
        Button(onClick = onGoMain) { Text(stringResource(Res.string.btn_go_main)) }
        Button(onClick = onGoContact) { Text(stringResource(Res.string.btn_go_contact)) }
    }
}
