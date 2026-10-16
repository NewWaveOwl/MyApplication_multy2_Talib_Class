package com.example.myapplication_multy2

import com.example.myapplication_multy2.viewmodels.MySimplestViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

class MySimplestViewModelTest {
    @Test
    fun settersPublishTheLatestStateFlowValues() {
        val viewModel = MySimplestViewModel()

        assertEquals("", viewModel.nameFlow.value)
        assertEquals(0, viewModel.counterFlow.value)

        viewModel.setName("x")
        viewModel.setName(viewModel.nameFlow.value + "x")
        viewModel.incrementCounter()
        viewModel.incrementCounter()

        assertEquals("xx", viewModel.nameFlow.value)
        assertEquals(2, viewModel.counterFlow.value)
    }
}
