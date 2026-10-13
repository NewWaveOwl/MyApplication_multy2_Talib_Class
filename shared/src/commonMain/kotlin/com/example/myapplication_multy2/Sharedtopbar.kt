package com.example.myapplication_multy2.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication_multy2.ColdFlowScreenKey
import com.example.myapplication_multy2.MySimpleScreenKey
import com.example.myapplication_multy2.MySimpleScreen2Key
import com.example.myapplication_multy2.MySimpleScreen3Key
import com.example.myapplication_multy2.ScreenKey
import com.example.myapplication_multy2.ScreenPreviewsKey
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.cd_go_back
import myapplication_multy2.shared.generated.resources.cd_menu
import myapplication_multy2.shared.generated.resources.ic_arrow_back_vector
import myapplication_multy2.shared.generated.resources.ic_menu_vector
import myapplication_multy2.shared.generated.resources.nav_seed10
import myapplication_multy2.shared.generated.resources.nav_seed20
import myapplication_multy2.shared.generated.resources.title_flow
import myapplication_multy2.shared.generated.resources.title_previews
import myapplication_multy2.shared.generated.resources.title_viewmodel
import myapplication_multy2.shared.generated.resources.title_viewmodel2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(
    screenTitle: String,
    currentScreen: ScreenKey?,
    canGoBack: Boolean,
    onBack: () -> Unit,
    onMySimple: () -> Unit,
    onMySimple2: () -> Unit,
    onSeed10: () -> Unit,
    onSeed20: () -> Unit,
    onFlow: () -> Unit,
    onPreviews: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    fun choose(action: () -> Unit) {
        menuExpanded = false
        action()
    }

    CenterAlignedTopAppBar(
        title = { Text(screenTitle) },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back_vector),
                        contentDescription = stringResource(Res.string.cd_go_back),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = {
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_menu_vector),
                        contentDescription = stringResource(Res.string.cd_menu),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DestinationMenuItem(
                        stringResource(Res.string.title_previews),
                        currentScreen is ScreenPreviewsKey
                    ) { choose(onPreviews) }
                    DestinationMenuItem(
                        stringResource(Res.string.title_flow),
                        currentScreen is ColdFlowScreenKey
                    ) { choose(onFlow) }
                    HorizontalDivider()
                    DestinationMenuItem(
                        stringResource(Res.string.title_viewmodel),
                        currentScreen is MySimpleScreenKey
                    ) { choose(onMySimple) }
                    DestinationMenuItem(
                        stringResource(Res.string.title_viewmodel2),
                        currentScreen is MySimpleScreen2Key
                    ) { choose(onMySimple2) }
                    DestinationMenuItem(
                        stringResource(Res.string.nav_seed10),
                        currentScreen is MySimpleScreen3Key && currentScreen.seed == 10
                    ) { choose(onSeed10) }
                    DestinationMenuItem(
                        stringResource(Res.string.nav_seed20),
                        currentScreen is MySimpleScreen3Key && currentScreen.seed == 20
                    ) { choose(onSeed20) }
                }
            }
        }
    )
}

@Composable
private fun DestinationMenuItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        },
        onClick = onClick,
        modifier = Modifier.semantics { selected = isSelected }
    )
}
