package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.MySimpleViewModel2
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.total_items_added
import org.jetbrains.compose.resources.stringResource

@Composable
fun MySimpleScreen2(
    key: Int = 0,
    myViewModel: MySimpleViewModel2 = viewModel(key = "$key") { MySimpleViewModel2() }
) {
    val count by myViewModel.count
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(Res.string.total_items_added, count))
        DisplayChangingList(
            theList = myViewModel.items,
            add = myViewModel::add,
            remove = myViewModel::remove
        )
    }
}
