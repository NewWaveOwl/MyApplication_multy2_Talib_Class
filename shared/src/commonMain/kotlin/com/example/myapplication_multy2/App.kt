package com.example.myapplication_multy2

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication_multy2.layout.MainLayout
import com.example.myapplication_multy2.model.SamplePeople
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.title_home
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = rememberNavBackStack(navSavedStateConfig, MainScreenKey)
        val navigator = remember(backStack) { Navigator(backStack) }
        val currentScreen = navigator.current as? ScreenKey
        val goHome: () -> Unit = { navigator.navigate(MainScreenKey) }
        val goAbout: () -> Unit = {
            navigator.navigate(AboutScreenKey(SamplePeople.about.name))
        }
        val goContact: () -> Unit = {
            navigator.navigate(ContactScreenKey(SamplePeople.contact.name, SamplePeople.contact.city))
        }

        val goMySimple: () -> Unit = { navigator.navigate(MySimpleScreenKey) }
        val goMySimple2: () -> Unit = { navigator.navigate(MySimpleScreen2Key) }

        CompositionLocalProvider(LocalNavigator provides navigator) {
            // Shared chrome stays outside NavDisplay's animated screen content.
            MainLayout(
                screenTitle = stringResource(currentScreen?.screenTitle ?: Res.string.title_home),
                currentScreen = currentScreen,
                canGoBack = navigator.hasPrevious(),
                onBack = navigator::pop,
                onHome = goHome,
                onAbout = goAbout,
                onContact = goContact,
                onMySimple = goMySimple,
                onMySimple2 = goMySimple2
            ) {
                NavDisplay(
                    backStack = backStack,
                    onBack = navigator::pop,
                    // Slide 34 exercise: ViewModels use the app root's owner.
                    // Popping a destination therefore retains its ViewModel state.
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator()
                    ),
                    entryProvider = entryProvider<NavKey> {
                        entry<MainScreenKey> {
                            MainScreen(
                                onGoAbout = goAbout,
                                onGoContact = goContact,
                                onGoViewModel = goMySimple
                            )
                        }
                        entry<MySimpleScreenKey> { MySimpleScreen() }
                        entry<MySimpleScreen2Key> { MySimpleScreen2() }
                        entry<AboutScreenKey> { key ->
                            AboutScreen(name = key.name, onGoMain = goHome, onGoContact = goContact)
                        }
                        entry<ContactScreenKey> { key ->
                            ContactScreen(
                                name = key.name,
                                city = key.city,
                                onGoMain = goHome,
                                onGoAbout = goAbout
                            )
                        }
                    }
                )
            }
        }
    }
}
