package com.example.myapplication_multy2.layout

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myapplication_multy2.AboutScreenKey
import com.example.myapplication_multy2.ContactScreenKey
import com.example.myapplication_multy2.LocalNavigator
import com.example.myapplication_multy2.MainScreenKey
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.cd_go_about
import myapplication_multy2.shared.generated.resources.cd_go_contact
import myapplication_multy2.shared.generated.resources.cd_go_home
import myapplication_multy2.shared.generated.resources.copyright
import myapplication_multy2.shared.generated.resources.fab_home
import myapplication_multy2.shared.generated.resources.ic_call
import myapplication_multy2.shared.generated.resources.ic_home
import myapplication_multy2.shared.generated.resources.ic_info
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SharedBottomBar() {
    val navigator = LocalNavigator.current

    BottomAppBar(
        actions = {
            IconButton(onClick = { navigator.navigate(MainScreenKey) }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_home),
                    contentDescription = stringResource(Res.string.cd_go_home),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { navigator.navigate(AboutScreenKey("Franz")) }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_info),
                    contentDescription = stringResource(Res.string.cd_go_about),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { navigator.navigate(ContactScreenKey("Julie", "Paris")) }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_call),
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
            FloatingActionButton(onClick = { navigator.navigate(MainScreenKey) }) {
                Text(stringResource(Res.string.fab_home))
            }
        },
    )
}