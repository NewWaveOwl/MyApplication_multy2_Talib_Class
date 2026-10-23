package com.example.myapplication_multy2
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.ContactViewModel
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_go_about
import myapplication_multy2.shared.generated.resources.btn_go_main
import myapplication_multy2.shared.generated.resources.contact_message
import org.jetbrains.compose.resources.stringResource
import myapplication_multy2.shared.generated.resources.info_contact

@Composable
fun ContactScreen(
    name: String,
    city: String,
    onGoMain: () -> Unit,
    onGoAbout: () -> Unit,
    myViewModel: ContactViewModel = viewModel { ContactViewModel(name, city) }
) {
    val person = myViewModel.person
    ContactContent(person.name, person.city, onGoMain, onGoAbout)
}

@Composable
fun ContactContent(name: String, city: String, onGoMain: () -> Unit, onGoAbout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ExerciseInfoButton(stringResource(Res.string.info_contact))
        Text(stringResource(Res.string.contact_message, name, city))
        Button(onClick = onGoMain) { Text(stringResource(Res.string.btn_go_main)) }
        Button(onClick = onGoAbout) { Text(stringResource(Res.string.btn_go_about)) }
    }
}
