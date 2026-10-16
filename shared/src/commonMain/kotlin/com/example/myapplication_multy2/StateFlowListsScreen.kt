package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.MySimplerViewModel
import com.example.myapplication_multy2.viewmodels.MySimplestViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.add_item
import myapplication_multy2.shared.generated.resources.list_approach_a
import myapplication_multy2.shared.generated.resources.list_approach_b
import myapplication_multy2.shared.generated.resources.list_empty
import org.jetbrains.compose.resources.stringResource

/** Exercise 8, slides 63-64: two ways to store a list in StateFlow. */
@Composable
fun StateFlowListsScreen(
    directListViewModel: MySimplestViewModel = viewModel(key = "exercise8-approach-a") {
        MySimplestViewModel()
    },
    profileViewModel: MySimplerViewModel = viewModel(key = "exercise8-approach-b") {
        MySimplerViewModel()
    }
) {
    val directItems by directListViewModel.items.collectAsState()
    val profileState by profileViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.list_approach_a),
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { directListViewModel.addItem(newTimestampedItem()) }) {
            Text(stringResource(Res.string.add_item))
        }
        StateFlowItemList(
            items = directItems,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        Text(
            text = stringResource(Res.string.list_approach_b),
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { profileViewModel.addItemToProfile(newTimestampedItem()) }) {
            Text(stringResource(Res.string.add_item))
        }
        StateFlowItemList(
            items = profileState.items,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}

@Composable
private fun StateFlowItemList(items: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (items.isEmpty()) {
            item { Text(stringResource(Res.string.list_empty)) }
        } else {
            items(items) { item ->
                Text(text = item, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
private fun newTimestampedItem(): String =
    "Item ${Clock.System.now().toEpochMilliseconds() % 1000}"
