package com.gildedrose

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ItemTest {

    @Test
    fun `An item is conjured if its name starts with Conjured`() {
        val item = Item(name = "Conjured Mountain Dew", sellIn = 900, quality = 21)
        assertTrue(item.isConjured)
    }

    @Test
    fun `An item is not conjured if its name does not start with Conjured`() {
        val item = Item(name = "Mountain Dew", sellIn = 900, quality = 20)
        assertFalse(item.isConjured)
    }
}