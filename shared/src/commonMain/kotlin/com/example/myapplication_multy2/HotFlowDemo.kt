package com.example.myapplication_multy2
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.info_hot_flow
import org.jetbrains.compose.resources.stringResource

/**
 * Slides 38 and 41:
 * A ViewModel that owns a hot StateFlow counter.
 */
class CounterViewModelHot : ViewModel() {

    // Mutable backing property. Only this ViewModel can change it.
    private val _count = MutableStateFlow(0)

    // Read-only StateFlow exposed to the UI.
    val count: StateFlow<Int> = _count.asStateFlow()

    init {
        // Starts when this ViewModel is created.
        viewModelScope.launch {
            while (true) {
                delay(1000)

                // Emit a new value through the StateFlow.
                _count.value += 1
            }
        }
    }
}

/**
 * Slides 39 and 41:
 * Collect the StateFlow as Compose State.
 */
@Composable
fun HotFlowDemo(
    viewModel: CounterViewModelHot = viewModel { CounterViewModelHot() }
) {
    // collectAsState converts StateFlow<Int> into Compose State<Int>.
    // The "by" delegate gives us the Int value directly.
    val count by viewModel.count.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExerciseInfoButton(stringResource(Res.string.info_hot_flow))
        Text(
            text = "Hot StateFlow Counter",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Count: $count",
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = "The ViewModel increases the value every second.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
