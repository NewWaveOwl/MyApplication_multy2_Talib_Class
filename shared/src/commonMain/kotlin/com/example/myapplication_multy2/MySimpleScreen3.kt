package com.example.myapplication_multy2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication_multy2.viewmodels.MySimpleViewModel3
import myapplication_multy2.shared.generated.resources.Res
import myapplication_multy2.shared.generated.resources.seed_value
import org.jetbrains.compose.resources.stringResource

@Composable
fun MySimpleScreen3(
    seed: Int,
    myViewModel: MySimpleViewModel3 = viewModel { MySimpleViewModel3(seed) }
) {
    val count by myViewModel.count
    Column {
        Text(stringResource(Res.string.seed_value, seed), modifier = Modifier.padding(8.dp))
        MySimpleContent2(count, myViewModel.items, myViewModel::add, myViewModel::remove)
    }
}
