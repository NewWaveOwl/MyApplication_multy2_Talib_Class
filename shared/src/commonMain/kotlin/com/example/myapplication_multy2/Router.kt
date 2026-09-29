package com.example.myapplication_multy2

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

// Routes
@Serializable
data object MainScreenKey : NavKey

@Serializable
data class AboutScreenKey(val name: String) : NavKey

@Serializable
data class ContactScreenKey(val name: String, val city: String) : NavKey

// Needed on non-Android targets so the back stack can be saved/restored
val navSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainScreenKey::class)
            subclass(AboutScreenKey::class)
            subclass(ContactScreenKey::class)
        }
    }
}