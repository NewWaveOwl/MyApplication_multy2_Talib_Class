package com.example.myapplication_multy2

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication_multy2.viewmodels.MySimpleViewModel2
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame
import kotlin.test.assertTrue

class MySimpleViewModel2Test {
    @Test
    fun listActionsUpdateCountOnlyWhenAnItemChanges() {
        val model = MySimpleViewModel2()
        assertEquals(0, model.count.value)
        assertTrue(model.items.isEmpty())

        model.add("First")
        model.add("Second")
        assertEquals(2, model.count.value)
        assertEquals(listOf("First", "Second"), model.items)

        model.remove("First")
        model.remove("Missing")
        assertEquals(1, model.count.value)
        assertEquals(listOf("Second"), model.items)

        model.remove("Second")
        model.remove("Second")
        assertEquals(0, model.count.value)
        assertTrue(model.items.isEmpty())
    }

    @Test
    fun initCopiesInitialItemsAndSetsCount() {
        val initialItems = mutableListOf("First", "Second")
        val model = MySimpleViewModel2(initialItems)
        initialItems.clear()

        assertEquals(listOf("First", "Second"), model.items)
        assertEquals(2, model.count.value)
        model.remove("First")
        assertEquals(1, model.count.value)
    }

    @Test
    fun duplicateValuesAreRemovedOneAtATime() {
        val model = MySimpleViewModel2()
        model.add("Same")
        model.add("Same")
        model.remove("Same")

        assertEquals(listOf("Same"), model.items)
        assertEquals(1, model.count.value)
    }

    @Test
    fun distinctKeysIsolateStateWhileSameKeyRetainsIt() {
        val store = ViewModelStore()
        val owner = object : ViewModelStoreOwner {
            override val viewModelStore = store
        }
        val factory = viewModelFactory { initializer { MySimpleViewModel2() } }
        try {
            val provider = ViewModelProvider.create(owner, factory)
            val first = provider["1", MySimpleViewModel2::class]
            val second = provider["2", MySimpleViewModel2::class]
            assertNotSame(first, second)

            first.add("First demo item")
            assertEquals(1, first.count.value)
            assertEquals(0, second.count.value)
            assertTrue(second.items.isEmpty())

            second.add("Second demo item")
            first.remove("First demo item")
            assertEquals(0, first.count.value)
            assertEquals(1, second.count.value)
            assertEquals(listOf("Second demo item"), second.items)

            val retained = ViewModelProvider.create(owner, factory)["2", MySimpleViewModel2::class]
            assertSame(second, retained)
            assertEquals(1, retained.count.value)
            assertEquals(listOf("Second demo item"), retained.items)

            store.clear()
            val fresh = ViewModelProvider.create(owner, factory)["2", MySimpleViewModel2::class]
            assertNotSame(second, fresh)
            assertEquals(0, fresh.count.value)
            assertTrue(fresh.items.isEmpty())
        } finally {
            store.clear()
        }
    }
}
