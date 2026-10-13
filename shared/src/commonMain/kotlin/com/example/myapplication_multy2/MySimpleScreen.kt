package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.MySimpleViewModel
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.btn_increment
import myapplication_multy2.shared.generated.resources.counter_value
import org.jetbrains.compose.resources.stringResource

@Composable
fun MySimpleScreen(
    myViewModel: MySimpleViewModel = viewModel { MySimpleViewModel() }
) {
    val count by myViewModel.count
    MySimpleContent(
        count = count,
        items = myViewModel.items,
        increment = myViewModel::increment,
        add = myViewModel::add,
        remove = myViewModel::remove
    )
}

@Composable
fun MySimpleContent(
    count: Int,
    items: List<String>,
    increment: () -> Unit,
    add: (String) -> Unit,
    remove: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = increment) { Text(stringResource(Res.string.btn_increment)) }
            Text(stringResource(Res.string.counter_value, count))
        }
        DisplayChangingList(
            theList = items,
            add = add,
            remove = remove,
            modifier = Modifier.weight(1f)
        )
    }
}
