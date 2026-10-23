package com.example.myapplication_multy2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Slide 17's console-only experiment blocks startup for one second.
        firstCoroutine()

        setContent {
            App()
        }
    }
}

/** Slide 17: runBlocking waits for its child; never call this from Compose. */
fun firstCoroutine() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
