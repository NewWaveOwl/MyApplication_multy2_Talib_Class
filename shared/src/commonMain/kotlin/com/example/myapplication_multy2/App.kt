package com.example.myapplication_multy2

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
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
// Set to true for the standard cleanup comparison from slides 40 and 42.
fun App(clearViewModelsOnPop: Boolean = false) {
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
        val goSeed10: () -> Unit = { navigator.navigate(MySimpleScreen3Key(seed = 10)) }
        val goSeed20: () -> Unit = { navigator.navigate(MySimpleScreen3Key(seed = 20)) }
        val goFlow: () -> Unit = { navigator.navigate(ColdFlowScreenKey) }
        val goPreviews: () -> Unit = { navigator.navigate(ScreenPreviewsKey) }
        val viewModelDecorator = if (clearViewModelsOnPop) {
            rememberViewModelStoreNavEntryDecorator<NavKey>()
        } else {
            rememberRetainedViewModelStoreNavEntryDecorator<NavKey>()
        }

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
                onMySimple2 = goMySimple2,
                onSeed10 = goSeed10,
                onSeed20 = goSeed20,
                onFlow = goFlow,
                onPreviews = goPreviews
            ) {
                NavDisplay(
                    backStack = backStack,
                    onBack = navigator::pop,
                    // Saveable state must wrap the ViewModel store decorator.
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        viewModelDecorator
                    ),
                    entryProvider = entryProvider<NavKey> {
                        entry<MainScreenKey> {
                            MainScreen(
                                onGoAbout = goAbout,
                                onGoContact = goContact,
                                onGoViewModel = goMySimple,
                                onGoFlow = goFlow,
                                onGoPreviews = goPreviews
                            )
                        }
                        entry<ColdFlowScreenKey> { ColdFlowDemo() }
                        entry<HotFlowScreenKey> { HotFlowDemo() }
                        entry<StateFlowScreenKey> { MySimplestScreen() }
                        entry<ObjectFlowScreenKey> { MySimplerScreen() }
                        entry<StateFlowListsScreenKey> { StateFlowListsScreen() }
                        entry<SharedFlowScreenKey> { SharedFlowScreen() }
                        entry<CoroutineDemoScreenKey> { CoroutineDemoScreen() }
                        entry<ScreenPreviewsKey> { ScreenPreviewsScreen(onOpenScreen = navigator::navigate) }
                        entry<MySimpleScreenKey> { MySimpleScreen() }
                        entry<MySimpleScreen2Key> { MySimpleScreen2() }
                        entry<MySimpleScreen3Key> { key -> MySimpleScreen3(seed = key.seed) }
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
