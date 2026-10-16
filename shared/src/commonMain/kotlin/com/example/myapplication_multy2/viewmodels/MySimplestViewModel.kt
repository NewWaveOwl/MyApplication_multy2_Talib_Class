package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Exercise 6, slides 49-54: simple values stored as StateFlow. */
class MySimplestViewModel : ViewModel() {
    private val _nameFlow = MutableStateFlow("")
    val nameFlow: StateFlow<String> = _nameFlow.asStateFlow()

    private val _counterFlow = MutableStateFlow(0)
    val counterFlow: StateFlow<Int> = _counterFlow.asStateFlow()

    // Exercise 8, Approach A: the list is its own StateFlow.
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items.asStateFlow()

    fun setName(name: String) {
        _nameFlow.value = name
    }

    fun incrementCounter() {
        _counterFlow.value += 1
    }

    fun addItem(item: String) {
        _items.update { currentList ->
            currentList + item
        }
    }
}
