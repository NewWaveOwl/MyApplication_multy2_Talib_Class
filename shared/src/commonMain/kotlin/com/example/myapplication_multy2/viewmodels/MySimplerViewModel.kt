package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication_multy2.model.ProfileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Exercise 7, slides 56-58: one object-based StateFlow with atomic updates. */
class MySimplerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileData())
    val uiState: StateFlow<ProfileData> = _uiState.asStateFlow()

    fun setName(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(name = newName)
        }
    }

    fun increment() {
        _uiState.update { currentState ->
            currentState.copy(counter = currentState.counter + 1)
        }
    }

    // Exercise 8, Approach B: the list belongs to ProfileData.
    fun addItemToProfile(item: String) {
        _uiState.update { currentState ->
            currentState.copy(items = currentState.items + item)
        }
    }
}
