package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication_multy2.model.Person

class AboutViewModel(name: String) : ViewModel() {
    // Route arguments are immutable for this navigation entry.
    val person = Person(name)
}
