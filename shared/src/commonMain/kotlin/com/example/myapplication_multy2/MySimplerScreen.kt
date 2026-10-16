package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.MySimplerViewModel
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.append_to_name
import myapplication_multy2.shared.generated.resources.counter_value
import myapplication_multy2.shared.generated.resources.increment_counter
import myapplication_multy2.shared.generated.resources.profile_name
import org.jetbrains.compose.resources.stringResource

/** Exercise 7, slides 56-58: collect and display one object-based StateFlow. */
@Composable
fun MySimplerScreen(
    myViewModel: MySimplerViewModel = viewModel { MySimplerViewModel() }
) {
    val uiState by myViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(Res.string.profile_name, uiState.name),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(Res.string.counter_value, uiState.counter),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(onClick = { myViewModel.setName(uiState.name + "x") }) {
            Text(stringResource(Res.string.append_to_name))
        }
        Button(onClick = myViewModel::increment) {
            Text(stringResource(Res.string.increment_counter))
        }
    }
}
