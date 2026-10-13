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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication_multy2.AboutScreenKey
import com.example.myapplication_multy2.ContactScreenKey
import com.example.myapplication_multy2.MainScreenKey
import com.example.myapplication_multy2.MySimpleScreenKey
import com.example.myapplication_multy2.MySimpleScreen2Key
import com.example.myapplication_multy2.MySimpleScreen3Key
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
import myapplication_multy2.shared.generated.resources.nav_seed10
import myapplication_multy2.shared.generated.resources.nav_seed20
import myapplication_multy2.shared.generated.resources.cd_seed10
import myapplication_multy2.shared.generated.resources.cd_seed20
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SharedBottomBar(
    currentScreen: ScreenKey?,
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onContact: () -> Unit,
    onMySimple: () -> Unit,
    onMySimple2: () -> Unit,
    onSeed10: () -> Unit,
    onSeed20: () -> Unit
) {
    val seed10Description = stringResource(Res.string.cd_seed10)
    val seed20Description = stringResource(Res.string.cd_seed20)
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
                label = { Text(stringResource(Res.string.title_viewmodel), maxLines = 2, textAlign = TextAlign.Center) }
            )
            NavigationBarItem(
                selected = currentScreen is MySimpleScreen2Key,
                onClick = onMySimple2,
                icon = { Text("2", style = MaterialTheme.typography.titleMedium) },
                label = { Text(stringResource(Res.string.title_viewmodel2), maxLines = 2, textAlign = TextAlign.Center) }
            )
            NavigationBarItem(
                selected = currentScreen is MySimpleScreen3Key && currentScreen.seed == 10,
                onClick = onSeed10,
                icon = {
                    Text("10", style = MaterialTheme.typography.titleMedium)
                },
                label = { Text(stringResource(Res.string.nav_seed10)) },
                modifier = Modifier.semantics {
                    contentDescription = seed10Description
                }
            )
            NavigationBarItem(
                selected = currentScreen is MySimpleScreen3Key && currentScreen.seed == 20,
                onClick = onSeed20,
                icon = {
                    Text("20", style = MaterialTheme.typography.titleMedium)
                },
                label = { Text(stringResource(Res.string.nav_seed20)) },
                modifier = Modifier.semantics {
                    contentDescription = seed20Description
                }
            )
        }
        Text(
            text = stringResource(Res.string.copyright),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
