package com.example.myapplication_multy2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlin.uuid.Uuid
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_add_item
import myapplication_multy2.shared.generated.resources.list_empty
import myapplication_multy2.shared.generated.resources.list_instructions
import org.jetbrains.compose.resources.stringResource

/** Reusable list UI: accepts state and actions, without depending on a ViewModel. */
@Composable
fun DisplayChangingList(
    theList: List<String>,
    add: (String) -> Unit,
    remove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { add("Item ${Uuid.random()}") }) {
                Text(stringResource(Res.string.btn_add_item))
            }
            Text(stringResource(Res.string.list_instructions), modifier = Modifier.weight(1f))
        }
        if (theList.isEmpty()) {
            Text(stringResource(Res.string.list_empty))
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp)) {
            items(theList) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                        .clickable(role = Role.Button) { remove(item) }
                        .padding(vertical = 12.dp, horizontal = 8.dp)
                )
            }
        }
    }
}
