package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Exercise 6, slides 49-54: simple values stored as StateFlow. */
class MySimplestViewModel : ViewModel() {
    private val _nameFlow = MutableStateFlow("")
    val nameFlow: StateFlow<String> = _nameFlow.asStateFlow()

    private val _counterFlow = MutableStateFlow(0)
    val counterFlow: StateFlow<Int> = _counterFlow.asStateFlow()

    fun setName(name: String) {
        _nameFlow.value = name
    }

    fun incrementCounter() {
        _counterFlow.value += 1
    }
}
