package com.example.myapplication_multy2

import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication_multy2.viewmodels.MySimpleViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame
import kotlin.test.assertTrue

class MySimpleViewModelTest {
    @Test
    fun counterAndListActionsRemainIndependent() {
        val model = MySimpleViewModel()
        assertEquals(0, model.count.value)
        assertTrue(model.items.isEmpty())

        repeat(3) { model.increment() }
        model.add("First")
        model.add("Second")
        model.remove("First")
        model.remove("Missing")

        assertEquals(3, model.count.value)
        assertEquals(listOf("Second"), model.items)
        model.remove("Second")
        assertTrue(model.items.isEmpty())
    }

    @Test
    fun listMutationsAreObservableThroughReadOnlyProperty() {
        val model = MySimpleViewModel()
        var observedReads = 0
        val snapshot = Snapshot.takeSnapshot(readObserver = { observedReads++ })
        try {
            snapshot.enter { assertEquals(0, model.items.size) }
        } finally {
            snapshot.dispose()
        }
        assertTrue(observedReads > 0)

        model.add("New item")
        assertEquals(listOf("New item"), model.items)
        model.remove("New item")
        assertTrue(model.items.isEmpty())
    }

    @Test
    fun retainedOwnerReusesStateAndClearedOwnerStartsFresh() {
        val store = ViewModelStore()
        val owner = object : ViewModelStoreOwner {
            override val viewModelStore = store
        }
        val factory = viewModelFactory { initializer { MySimpleViewModel() } }
        try {
            val original = ViewModelProvider.create(owner, factory)[MySimpleViewModel::class]
            original.increment()
            original.add("Retained item")

            // A recreated UI looks up its model again through the retained store.
            val retained = ViewModelProvider.create(owner, factory)[MySimpleViewModel::class]
            assertSame(original, retained)
            assertEquals(1, retained.count.value)
            assertEquals(listOf("Retained item"), retained.items)

            store.clear()
            val fresh = ViewModelProvider.create(owner, factory)[MySimpleViewModel::class]
            assertNotSame(original, fresh)
            assertEquals(0, fresh.count.value)
            assertTrue(fresh.items.isEmpty())
        } finally {
            store.clear()
        }
    }
}
