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
import com.example.myapplication_multy2.viewmodels.MySimplestViewModel
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.append_to_name
import myapplication_multy2.shared.generated.resources.counter_is
import myapplication_multy2.shared.generated.resources.increment_counter
import myapplication_multy2.shared.generated.resources.welcome_name
import org.jetbrains.compose.resources.stringResource
import myapplication_multy2.shared.generated.resources.info_state_flow

/** Exercise 6, slides 49-54: collect and change two ViewModel StateFlows. */
@Composable
fun MySimplestScreen(
    myViewModel: MySimplestViewModel = viewModel { MySimplestViewModel() }
) {
    val name by myViewModel.nameFlow.collectAsState()
    val counter by myViewModel.counterFlow.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        ExerciseInfoButton(stringResource(Res.string.info_state_flow))
        Text(
            text = stringResource(Res.string.welcome_name, name),
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = { myViewModel.setName(name + "x") }) {
            Text(stringResource(Res.string.append_to_name))
        }

        Text(
            text = stringResource(Res.string.counter_is, counter),
            style = MaterialTheme.typography.headlineSmall
        )
        Button(onClick = myViewModel::incrementCounter) {
            Text(stringResource(Res.string.increment_counter))
        }
    }
}
