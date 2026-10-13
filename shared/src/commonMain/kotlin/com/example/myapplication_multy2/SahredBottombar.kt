package com.example.myapplication_multy2.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication_multy2.AboutScreenKey
import com.example.myapplication_multy2.ContactScreenKey
import com.example.myapplication_multy2.MainScreenKey
import com.example.myapplication_multy2.MySimpleScreenKey
import com.example.myapplication_multy2.MySimpleScreen2Key
import com.example.myapplication_multy2.ScreenKey
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.copyright
import myapplication_multy2.shared.generated.resources.ic_call_vector
import myapplication_multy2.shared.generated.resources.ic_home_vector
import myapplication_multy2.shared.generated.resources.ic_info_vector
import myapplication_multy2.shared.generated.resources.title_about
import myapplication_multy2.shared.generated.resources.title_contact
import myapplication_multy2.shared.generated.resources.title_home
import myapplication_multy2.shared.generated.resources.title_viewmodel
import myapplication_multy2.shared.generated.resources.title_viewmodel2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SharedBottomBar(
    currentScreen: ScreenKey?,
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onContact: () -> Unit,
    onMySimple: () -> Unit,
    onMySimple2: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().navigationBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
        NavigationBar(windowInsets = WindowInsets(0, 0, 0, 0)) {
            NavigationBarItem(
                selected = currentScreen is MainScreenKey,
                onClick = { if (currentScreen !is MainScreenKey) onHome() },
                icon = { Icon(painterResource(Res.drawable.ic_home_vector), contentDescription = null) },
                label = { Text(stringResource(Res.string.title_home)) }
            )
            NavigationBarItem(
                selected = currentScreen is AboutScreenKey,
                onClick = { if (currentScreen !is AboutScreenKey) onAbout() },
                icon = { Icon(painterResource(Res.drawable.ic_info_vector), contentDescription = null) },
                label = { Text(stringResource(Res.string.title_about)) }
            )
            NavigationBarItem(
                selected = currentScreen is ContactScreenKey,
                onClick = { if (currentScreen !is ContactScreenKey) onContact() },
                icon = { Icon(painterResource(Res.drawable.ic_call_vector), contentDescription = null) },
                label = { Text(stringResource(Res.string.title_contact)) }
            )
            NavigationBarItem(
                selected = currentScreen is MySimpleScreenKey,
                onClick = onMySimple,
                icon = { Text("1", style = MaterialTheme.typography.titleMedium) },
                label = { Text(stringResource(Res.string.title_viewmodel)) }
            )
            NavigationBarItem(
                selected = currentScreen is MySimpleScreen2Key,
                onClick = onMySimple2,
                icon = { Text("2", style = MaterialTheme.typography.titleMedium) },
                label = { Text(stringResource(Res.string.title_viewmodel2)) }
            )
        }
        Text(
            text = stringResource(Res.string.copyright),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
