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

/** Navigation exercise: the closest owner supplies the model, without a manual key. */
@Composable
fun MySimpleScreen2(
    myViewModel: MySimpleViewModel2 = viewModel { MySimpleViewModel2() }
) {
    val count by myViewModel.count
    MySimpleContent2(count, myViewModel.items, myViewModel::add, myViewModel::remove)
}

/** Slide 31's keyed variant, retained for the two independent demos on Home. */
@Composable
fun MySimpleScreen2a(
    key: Int = 0,
    myViewModel: MySimpleViewModel2 = viewModel(key = "$key") { MySimpleViewModel2() }
) {
    val count by myViewModel.count
    MySimpleContent2(count, myViewModel.items, myViewModel::add, myViewModel::remove)
}

@Composable
fun MySimpleContent2(
    count: Int,
    items: List<String>,
    add: (String) -> Unit,
    remove: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(Res.string.total_items_added, count))
        DisplayChangingList(theList = items, add = add, remove = remove)
    }
}
