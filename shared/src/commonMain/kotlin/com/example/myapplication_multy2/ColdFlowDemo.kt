package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.collector_number
import myapplication_multy2.shared.generated.resources.flow_explanation
import myapplication_multy2.shared.generated.resources.latest_value
import myapplication_multy2.shared.generated.resources.no_value_yet
import org.jetbrains.compose.resources.stringResource

/** Slides 20-22: collect a cold flow from two independent Compose coroutines. */
@Composable
fun ColdFlowDemo(numbers: Flow<Int> = remember { coldNumbersFlow() }) {
    val scope = rememberCoroutineScope()
    var latestValue1 by rememberSaveable { mutableStateOf<Int?>(null) }
    var latestValue2 by rememberSaveable { mutableStateOf<Int?>(null) }
    var collecting1 by remember { mutableStateOf(false) }
    var collecting2 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(Res.string.flow_explanation))
        CollectorPanel(
            number = 1,
            latestValue = latestValue1,
            collecting = collecting1,
            onStart = {
                collecting1 = true
                scope.launch {
                    try {
                        numbers.collect { latestValue1 = it }
                    } finally {
                        collecting1 = false
                    }
                }
            }
        )
        CollectorPanel(
            number = 2,
            latestValue = latestValue2,
            collecting = collecting2,
            onStart = {
                collecting2 = true
                scope.launch {
                    try {
                        numbers.collect { latestValue2 = it }
                    } finally {
                        collecting2 = false
                    }
                }
            }
        )
    }
}

@Composable
private fun CollectorPanel(number: Int, latestValue: Int?, collecting: Boolean, onStart: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onStart, enabled = !collecting) {
                Text(stringResource(Res.string.collector_number, number))
            }
            Text(
                text = latestValue?.let { stringResource(Res.string.latest_value, it) }
                    ?: stringResource(Res.string.no_value_yet),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
