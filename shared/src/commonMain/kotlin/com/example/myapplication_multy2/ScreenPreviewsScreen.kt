package com.example.myapplication_multy2

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreProvider
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.nav_seed10
import myapplication_multy2.shared.generated.resources.nav_seed20
import myapplication_multy2.shared.generated.resources.open_full_screen
import myapplication_multy2.shared.generated.resources.preview_explanation
import myapplication_multy2.shared.generated.resources.preview_screen1
import myapplication_multy2.shared.generated.resources.preview_screen2
import myapplication_multy2.shared.generated.resources.title_hot_flow
import myapplication_multy2.shared.generated.resources.title_object_flow
import myapplication_multy2.shared.generated.resources.title_state_flow
import myapplication_multy2.shared.generated.resources.title_state_flow_lists
import myapplication_multy2.shared.generated.resources.title_shared_flow
import org.jetbrains.compose.resources.stringResource

/** Interactive product feature for comparing the existing screen variants. */
@Composable
fun ScreenPreviewsScreen(onOpenScreen: (ScreenKey) -> Unit) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val targets = listOf(
        MySimpleScreenKey,
        MySimpleScreen2Key,
        MySimpleScreen3Key(10),
        MySimpleScreen3Key(20),
        HotFlowScreenKey,
        StateFlowScreenKey,
        ObjectFlowScreenKey,
        StateFlowListsScreenKey,
        SharedFlowScreenKey
    )
    val labels = listOf(
        stringResource(Res.string.preview_screen1),
        stringResource(Res.string.preview_screen2),
        stringResource(Res.string.nav_seed10),
        stringResource(Res.string.nav_seed20),
        stringResource(Res.string.title_hot_flow),
        stringResource(Res.string.title_state_flow),
        stringResource(Res.string.title_object_flow),
        stringResource(Res.string.title_state_flow_lists),
        stringResource(Res.string.title_shared_flow)
    )
    // Changing the selection swaps owners, so previews of the same model class
    // remain independent. The provider survives rotation and releases its stores
    // when this preview session leaves composition.
    val provider = rememberViewModelStoreProvider()
    val selected = targets[selectedIndex]
    val owner = rememberViewModelStoreOwner(key = selected, provider = provider)

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(Res.string.preview_explanation))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            labels.forEachIndexed { index, label ->
                FilterChip(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    label = { Text(label) }
                )
            }
        }
        OutlinedButton(onClick = { onOpenScreen(selected) }) {
            Text(stringResource(Res.string.open_full_screen))
        }
        Box(modifier = Modifier.fillMaxWidth().height(480.dp)) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
                when (selected) {
                    MySimpleScreenKey -> MySimpleScreen()
                    MySimpleScreen2Key -> MySimpleScreen2()
                    is MySimpleScreen3Key -> MySimpleScreen3(seed = selected.seed)
                    HotFlowScreenKey -> HotFlowDemo()
                    StateFlowScreenKey -> MySimplestScreen()
                    ObjectFlowScreenKey -> MySimplerScreen()
                    StateFlowListsScreenKey -> StateFlowListsScreen()
                    SharedFlowScreenKey -> SharedFlowScreen()
                    else -> error("Unsupported preview: $selected")
                }
            }
        }
    }
}
