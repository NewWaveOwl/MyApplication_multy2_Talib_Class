package com.example.myapplication_multy2

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = rememberNavBackStack(navSavedStateConfig, MainScreenKey)
        val navigator = remember(backStack) { Navigator(backStack) }

        CompositionLocalProvider(LocalNavigator provides navigator) {
            NavDisplay(
                backStack = backStack,
                onBack = { navigator.pop() },
                entryProvider = entryProvider<NavKey> {
                    entry<MainScreenKey> { MainScreen() }
                    entry<AboutScreenKey> { key -> AboutScreen(name = key.name) }
                    entry<ContactScreenKey> { key ->
                        ContactScreen(name = key.name, city = key.city)
                    }
                }
            )
        }
    }
}