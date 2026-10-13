package com.example.myapplication_multy2.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/** List and counter exercise from slides 23-27, reused with keys on slide 31. */
class MySimpleViewModel2(initialItems: List<String> = emptyList()) : ViewModel() {
    private val _count = mutableIntStateOf(0)
    val count: State<Int>
        get() = _count

    // Kotlin 2.4 explicit backing field keeps mutations inside this ViewModel.
    val items: List<String>
        field = mutableStateListOf<String>()

    // Runs once per instance, rather than every time its screen recomposes.
    init {
        items.addAll(initialItems)
        _count.intValue = items.size
    }

    fun increment() {
        _count.intValue += 1
    }

    fun decrement() {
        _count.intValue -= 1
    }

    fun add(item: String) {
        items.add(item)
        increment()
    }

    fun remove(item: String) {
        if (items.remove(item)) {
            decrement()
        }
    }
}
