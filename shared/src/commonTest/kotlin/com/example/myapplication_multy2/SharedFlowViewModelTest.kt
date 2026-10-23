package com.example.myapplication_multy2

import com.example.myapplication_multy2.viewmodels.SharedFlowViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

/** Exercises delivery under the same slow-collector conditions as the UI. */
@OptIn(ExperimentalCoroutinesApi::class)
class SharedFlowViewModelTest {
    @Test
    fun suspendPatternsDeliverEveryRapidClickInOrder() = sharedFlowTest {
        for (capacity in 0..1) {
            val viewModel = SharedFlowViewModel(extraBufferCapacity = capacity)
            val received = mutableListOf<String>()
            val collector = launch {
                viewModel.events.collect {
                    delay(500)
                    received.add(it)
                }
            }
            runCurrent()
            repeat(6) { viewModel.emitEvent() }
            advanceUntilIdle()
            assertEquals((1..6).map { "Event $it" }, received)
            collector.cancel()
        }
    }

    @Test
    fun dropOldestKeepsLatestQueuedEventForSlowCollector() = sharedFlowTest {
        val viewModel = SharedFlowViewModel(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        val received = mutableListOf<String>()
        val collector = launch {
            viewModel.events.collect {
                delay(500)
                received.add(it)
            }
        }
        runCurrent()
        viewModel.emitEvent()
        runCurrent() // The first event is now being processed.
        repeat(5) { viewModel.emitEvent() }
        advanceUntilIdle()
        assertEquals(listOf("Event 1", "Event 6"), received)
        collector.cancel()
    }

    @Test
    fun noReplayDoesNotQueueEventsForAbsentCollector() = sharedFlowTest {
        val viewModel = SharedFlowViewModel(extraBufferCapacity = 1)
        viewModel.emitEvent()
        runCurrent()
        val received = mutableListOf<String>()
        val collector = launch { viewModel.events.collect { received.add(it) } }
        runCurrent()
        assertEquals(emptyList(), received)
        viewModel.emitEvent()
        runCurrent()
        assertEquals(listOf("Event 2"), received)
        collector.cancel()
    }

    @Test
    fun replayDeliversLastEventToNewCollector() = sharedFlowTest {
        val viewModel = SharedFlowViewModel(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        repeat(3) { viewModel.emitEvent() }
        runCurrent()
        val received = mutableListOf<String>()
        val collector = launch { viewModel.events.collect { received.add(it) } }
        runCurrent()
        assertEquals(listOf("Event 3"), received)
        collector.cancel()
    }

    /**
     * Installs a controlled Main dispatcher for viewModelScope and virtual time.
     *
     * @param block Behavior to verify with the test scheduler.
     */
    private fun sharedFlowTest(block: suspend TestScope.() -> Unit) = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            block()
        } finally {
            Dispatchers.resetMain()
        }
    }
}
