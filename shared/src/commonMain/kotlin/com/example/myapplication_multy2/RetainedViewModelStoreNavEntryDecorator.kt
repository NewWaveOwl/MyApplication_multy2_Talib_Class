package com.example.myapplication_multy2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreProvider
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.savedstate.compose.LocalSavedStateRegistryOwner

/**
 * Implements the retained-store exercise using Lifecycle 2.11's supported APIs.
 * The old rememberViewModelStoreNavEntryDecorator { false } overload is deprecated:
 * its callback is ignored in 2.11, so it would still clear stores after popping.
 * Stores here are shared by route contentKey, including route arguments. They are
 * retained after pop and cleaned up when their parent provider is destroyed.
 */
@Composable
fun <T : Any> rememberRetainedViewModelStoreNavEntryDecorator(): NavEntryDecorator<T> {
    val provider = rememberViewModelStoreProvider()
    return remember(provider) {
        NavEntryDecorator(
            // No onPop cleanup: retain each route's store for a later visit.
            decorate = { entry ->
                val owner = rememberViewModelStoreOwner(
                    key = entry.contentKey,
                    provider = provider,
                    savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
                )
                CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
                    entry.Content()
                }
            }
        )
    }
}
