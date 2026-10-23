package com.example.myapplication_multy2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Emits numbered events for exercise 9; no event history is stored here.
 *
 * @param replay Number of recent events delivered to a new collector.
 * @param extraBufferCapacity Extra queued events for a slow active collector.
 * @param onBufferOverflow Behavior when an active collector exhausts the buffer.
 */
class SharedFlowViewModel(
    replay: Int = 0,
    extraBufferCapacity: Int = 0,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ViewModel() {
    private val _events = MutableSharedFlow<String>(
        replay = replay,
        extraBufferCapacity = extraBufferCapacity,
        onBufferOverflow = onBufferOverflow
    )

    /** Hot events; without subscribers only the configured replay is retained. */
    val events: SharedFlow<String> = _events.asSharedFlow()

    private var counter = 0

    /** Emits the next label in the ViewModel scope; called on the UI thread. */
    fun emitEvent() {
        viewModelScope.launch {
            counter++
            _events.emit("Event $counter")
        }
    }
}
