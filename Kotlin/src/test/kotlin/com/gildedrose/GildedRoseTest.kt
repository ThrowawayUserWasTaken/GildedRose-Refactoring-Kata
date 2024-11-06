package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun foo() {
        val name = "foo"
        val items = listOf(Item(name, 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(name, app.items[0].name)
    }
}


