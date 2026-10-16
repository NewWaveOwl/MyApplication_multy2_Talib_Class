package com.example.myapplication_multy2

import com.example.myapplication_multy2.model.ProfileData
import com.example.myapplication_multy2.viewmodels.MySimplerViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

class MySimplerViewModelTest {
    @Test
    fun objectUpdatesChangeOneFieldAndPreserveTheOther() {
        val viewModel = MySimplerViewModel()

        assertEquals(ProfileData(), viewModel.uiState.value)

        viewModel.setName("xx")
        assertEquals(ProfileData(name = "xx", counter = 0), viewModel.uiState.value)

        viewModel.increment()
        viewModel.increment()
        assertEquals(ProfileData(name = "xx", counter = 2), viewModel.uiState.value)
    }
}
