package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Day 25, slides 18–21: small scope, suspend-function, and effect examples. */
@Composable
fun CoroutineDemoScreen() {
    var userId by remember { mutableStateOf("1") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MyButton()
        Button(onClick = { userId = if (userId == "1") "2" else "1" }) {
            Text("Change user")
        }
        UserProfile(userId)
    }
}

/** Slide 19: button work uses the scope owned by this composition. */
@Composable
fun MyButton() {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            println("Hello")
            delay(1000L)
            println("World!")
        }
    }) {
        Text("Click me")
    }
}

/**
 * Slide 20: changing the key cancels the old fetch and starts the new one.
 *
 * @param userId Identifier used by the mock fetch.
 */
@Composable
fun UserProfile(userId: String) {
    var data by remember(userId) { mutableStateOf("Loading...") }

    LaunchedEffect(userId) {
        data = fetchUserData(userId)
    }
    Text(data)
}

/**
 * Slide 18: suspends without starting a coroutine or blocking its thread.
 *
 * @param userId Identifier to include in the mock response.
 * @return Mock user data after a one-second delay; no network request is made.
 */
suspend fun fetchUserData(userId: String): String {
    delay(1000L)
    return "User $userId"
}
