package com.example.myapplication_multy2
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.SharedFlowViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.SharedFlow
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.shared_flow_buffered
import myapplication_multy2.shared.generated.resources.shared_flow_click_count
import myapplication_multy2.shared.generated.resources.shared_flow_drop_oldest
import myapplication_multy2.shared.generated.resources.shared_flow_emit
import myapplication_multy2.shared.generated.resources.shared_flow_explanation
import myapplication_multy2.shared.generated.resources.shared_flow_received_count
import myapplication_multy2.shared.generated.resources.shared_flow_replay
import myapplication_multy2.shared.generated.resources.shared_flow_strict
import org.jetbrains.compose.resources.stringResource
import myapplication_multy2.shared.generated.resources.info_shared_flow

/** Exercise 9, slides 70–72: compare the four slide-table configurations. */
@Composable
fun SharedFlowScreen() {
    var selectedPattern by rememberSaveable { mutableIntStateOf(0) }
    val labels = listOf(
        stringResource(Res.string.shared_flow_strict),
        stringResource(Res.string.shared_flow_buffered),
        stringResource(Res.string.shared_flow_drop_oldest),
        stringResource(Res.string.shared_flow_replay)
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExerciseInfoButton(stringResource(Res.string.info_shared_flow))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            labels.forEachIndexed { index, label ->
                FilterChip(
                    selected = selectedPattern == index,
                    onClick = { selectedPattern = index },
                    label = { Text(label) }
                )
            }
        }
        Text(stringResource(Res.string.shared_flow_explanation))
        // Each fixed pattern has its own ViewModel; changing tabs resets UI history.
        key(selectedPattern) {
            val eventViewModel: SharedFlowViewModel = viewModel(key = "exercise9-$selectedPattern") {
                SharedFlowViewModel(
                    replay = if (selectedPattern == 3) 1 else 0,
                    extraBufferCapacity = if (selectedPattern in 1..2) 1 else 0,
                    onBufferOverflow = if (selectedPattern >= 2) {
                        BufferOverflow.DROP_OLDEST
                    } else {
                        BufferOverflow.SUSPEND
                    }
                )
            }
            SharedFlowEventList(eventViewModel.events, eventViewModel::emitEvent)
        }
    }
}

/**
 * Counts local clicks and accumulates received events with the lecture's delay.
 *
 * @param events Numbered hot stream for the selected pattern.
 * @param onEmit Requests the next event after a click.
 */
@Composable
private fun SharedFlowEventList(events: SharedFlow<String>, onEmit: () -> Unit) {
    var count by remember { mutableIntStateOf(0) }
    val eventList = remember { mutableStateListOf<String>() }

    LaunchedEffect(events) {
        // collectLatest would cancel the delay and hide the buffering comparison.
        events.collect { message ->
            delay(500L)
            eventList.add(message)
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {
            count++
            onEmit()
        }) {
            Text(stringResource(Res.string.shared_flow_emit))
        }
        Text(stringResource(Res.string.shared_flow_click_count, count))
        Text(stringResource(Res.string.shared_flow_received_count, eventList.size))
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(eventList) { message -> Text(message) }
        }
    }
}
