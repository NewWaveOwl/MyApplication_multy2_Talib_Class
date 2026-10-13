package com.example.myapplication_multy2

import com.example.myapplication_multy2.viewmodels.MySimpleViewModel3
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MySimpleViewModel3Test {
    @Test
    fun listActionsPreserveTheInitialSeedOffset() {
        for (seed in listOf(10, 20)) {
            val model = MySimpleViewModel3(seed)
            assertEquals(seed, model.count.value)
            assertTrue(model.items.isEmpty())

            model.add("First")
            model.add("Second")
            assertEquals(seed + 2, model.count.value)
            model.remove("First")
            model.remove("Missing")
            assertEquals(seed + 1, model.count.value)
            assertEquals(listOf("Second"), model.items)
            model.remove("Second")
            assertEquals(seed, model.count.value)
            assertTrue(model.items.isEmpty())
        }
    }
}
