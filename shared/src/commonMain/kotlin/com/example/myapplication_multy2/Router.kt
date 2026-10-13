package com.example.myapplication_multy2

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.title_about
import myapplication_multy2.shared.generated.resources.title_contact
import myapplication_multy2.shared.generated.resources.title_home
import myapplication_multy2.shared.generated.resources.title_viewmodel
import org.jetbrains.compose.resources.StringResource

/** Closed set of destinations, each supplying its localized title resource. */
sealed interface ScreenKey : NavKey {
    val screenTitle: StringResource
}

@Serializable
data object MainScreenKey : ScreenKey {
    override val screenTitle: StringResource get() = Res.string.title_home
}

@Serializable
data object MySimpleScreenKey : ScreenKey {
    override val screenTitle: StringResource get() = Res.string.title_viewmodel
}

@Serializable
data class AboutScreenKey(val name: String) : ScreenKey {
    override val screenTitle: StringResource get() = Res.string.title_about
}

@Serializable
data class ContactScreenKey(val name: String, val city: String) : ScreenKey {
    override val screenTitle: StringResource get() = Res.string.title_contact
}

// Needed on non-Android targets so the back stack can be saved/restored
val navSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainScreenKey::class)
            subclass(MySimpleScreenKey::class)
            subclass(AboutScreenKey::class)
            subclass(ContactScreenKey::class)
        }
    }
}
