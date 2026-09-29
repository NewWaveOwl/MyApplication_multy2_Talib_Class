package com.example.myapplication_multy2

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Multiplatform-friendly navigation helper for Navigation3.
 * Small, explicit API for manipulating a NavBackStack.
 */
class Navigator(
    private val backStack: NavBackStack<NavKey>
) {
    /** The current (top) destination, or null if the stack is empty. */
    val current: NavKey?
        get() = backStack.lastOrNull()

    /** Push a new screen onto the back stack. */
    fun navigate(key: NavKey) {
        backStack += key
    }

    /** Remove the current screen and go back. Does nothing if 0 or 1 entries. */
    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    /** Returns true if a previous destination exists. */
    fun hasPrevious(): Boolean = backStack.size > 1

    /** Pop back to a specific screen. Unchanged if the key isn't found. */
    fun popUntil(key: NavKey) {
        val index = backStack.indexOfLast { it == key }
        if (index == -1) return

        while (backStack.lastIndex > index) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    /** Replace the current top screen with a new one. */
    fun replace(key: NavKey) {
        if (backStack.isNotEmpty()) {
            backStack.removeAt(backStack.lastIndex)
        }
        backStack += key
    }
}

/** Provider: App supplies the Navigator, screens read LocalNavigator.current */
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided")
}