package com.example.myapplication_multy2.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/** Counter (slides 14–18) and observable string list (slides 19–22). */
class MySimpleViewModel : ViewModel() {
    // Traditional backing property: mutable inside, read-only outside.
    private val _count = mutableIntStateOf(0)
    val count: State<Int>
        get() = _count

    // Kotlin 2.4 explicit backing field, introduced on slide 21.
    // SnapshotStateList mutations still notify Compose through the List API.
    val items: List<String>
        field = mutableStateListOf<String>()

    fun increment() {
        _count.intValue += 1
    }

    fun add(item: String) {
        items.add(item)
    }

    fun remove(item: String) {
        items.remove(item)
    }
}
