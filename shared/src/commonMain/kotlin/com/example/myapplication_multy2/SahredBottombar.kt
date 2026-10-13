package com.example.myapplication_multy2.layout

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.myapplication_multy2.AboutScreenKey
import com.example.myapplication_multy2.ContactScreenKey
import com.example.myapplication_multy2.MainScreenKey
import com.example.myapplication_multy2.ScreenKey
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.cd_go_about
import myapplication_multy2.shared.generated.resources.cd_go_contact
import myapplication_multy2.shared.generated.resources.cd_go_home
import myapplication_multy2.shared.generated.resources.copyright
import myapplication_multy2.shared.generated.resources.fab_home
import myapplication_multy2.shared.generated.resources.ic_call_vector
import myapplication_multy2.shared.generated.resources.ic_home_vector
import myapplication_multy2.shared.generated.resources.ic_info_vector
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SharedBottomBar(
    currentScreen: ScreenKey?,
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onContact: () -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = { if (currentScreen !is MainScreenKey) onHome() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (currentScreen is MainScreenKey)
                        MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_home_vector),
                    contentDescription = stringResource(Res.string.cd_go_home),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { if (currentScreen !is AboutScreenKey) onAbout() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (currentScreen is AboutScreenKey)
                        MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_info_vector),
                    contentDescription = stringResource(Res.string.cd_go_about),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { if (currentScreen !is ContactScreenKey) onContact() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (currentScreen is ContactScreenKey)
                        MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_call_vector),
                    contentDescription = stringResource(Res.string.cd_go_contact),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = stringResource(Res.string.copyright),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onHome) {
                Text(stringResource(Res.string.fab_home))
            }
        },
    )
}
