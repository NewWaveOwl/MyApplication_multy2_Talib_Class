package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication_multy2.model.Person

class ContactViewModel(name: String, city: String) : ViewModel() {
    val person = Person(name, city)
}
