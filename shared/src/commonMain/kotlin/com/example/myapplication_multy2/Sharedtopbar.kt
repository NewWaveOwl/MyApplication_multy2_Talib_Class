package com.example.myapplication_multy2.layout

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myapplication_multy2.LocalNavigator
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.cd_go_back
import myapplication_multy2.shared.generated.resources.cd_menu
import myapplication_multy2.shared.generated.resources.ic_arrow_back
import myapplication_multy2.shared.generated.resources.ic_menu
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(screenTitle: String) {
    val navigator = LocalNavigator.current

    CenterAlignedTopAppBar(
        title = { Text(screenTitle) },
        navigationIcon = {
            // Gated: only show Back when there is something to go back to
            if (navigator.hasPrevious()) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.cd_go_back),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_menu),
                    contentDescription = stringResource(Res.string.cd_menu),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
    )
}