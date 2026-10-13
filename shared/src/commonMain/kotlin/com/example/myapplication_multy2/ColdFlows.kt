package com.example.myapplication_multy2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/** Slides 14-18: each collection executes the producer again from the start. */
fun coldNumbersFlow(intervalMillis: Long = 500L): Flow<Int> = flow {
    for (number in 1..3) {
        emit(number)
        delay(intervalMillis)
    }
}

/** Slide 15: alternatives for already-known values and existing collections. */
fun fixedNumbersFlow(): Flow<Int> = flowOf(1, 2, 3)

fun collectionNumbersFlow(): Flow<Int> = listOf(1, 2, 3).asFlow()
