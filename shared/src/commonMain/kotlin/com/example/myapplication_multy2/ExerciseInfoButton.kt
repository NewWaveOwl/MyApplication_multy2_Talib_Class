package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.exercise_info
import myapplication_multy2.shared.generated.resources.exercise_info_close
import myapplication_multy2.shared.generated.resources.ic_info_vector
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Opens scrollable exercise instructions without navigating away from the demo.
 *
 * @param instructions Steps to try and the results the learner should expect.
 */
@Composable
fun ExerciseInfoButton(instructions: String) {
    var showInfo by remember(instructions) { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = { showInfo = true }) {
            Icon(
                painter = painterResource(Res.drawable.ic_info_vector),
                contentDescription = stringResource(Res.string.exercise_info)
            )
        }
    }
    if (showInfo) {
        AlertDialog(
            onDismissRequest = { showInfo = false },
            title = { Text(stringResource(Res.string.exercise_info)) },
            text = {
                Text(instructions, modifier = Modifier.verticalScroll(rememberScrollState()))
            },
            confirmButton = {
                TextButton(onClick = { showInfo = false }) {
                    Text(stringResource(Res.string.exercise_info_close))
                }
            }
        )
    }
}
