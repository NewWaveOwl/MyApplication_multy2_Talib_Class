package com.example.myapplication_multy2

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ColdFlowsTest {
    @Test
    fun collectorsExecuteIndependentlyAndOneCanFinishEarly() = runBlocking {
        val numbers = coldNumbersFlow(intervalMillis = 10)
        val first = async { numbers.take(1).toList() }
        val second = async { numbers.toList() }

        assertEquals(listOf(1), first.await())
        assertEquals(listOf(1, 2, 3), second.await())
        // Collecting the same completed flow starts a fresh producer.
        assertEquals(listOf(1, 2, 3), numbers.toList())
    }

    @Test
    fun fixedAndCollectionBuildersEmitTheSameSequence() = runBlocking {
        assertEquals(listOf(1, 2, 3), fixedNumbersFlow().toList())
        assertEquals(listOf(1, 2, 3), collectionNumbersFlow().toList())
    }
}
