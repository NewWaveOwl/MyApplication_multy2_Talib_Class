package com.example.myapplication_multy2.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/** Slide 42: each route's seed supplies the initial counter value. */
class MySimpleViewModel3(seed: Int) : ViewModel() {
    private val _count = mutableIntStateOf(seed)
    val count: State<Int>
        get() = _count

    val items: List<String>
        field = mutableStateListOf<String>()

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
