package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    @DisplayName("For Aged Brie")
    inner class AgedBrieTest {

        @Test
        fun `The quality increases`() {
            val item = Item(name = AGED_BRIE_NAME, sellIn = 12, quality = 0)
            val expectedQuality = item.quality + 1

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(expectedQuality, app.items[0].quality)
        }

        @Test
        fun `The quality does not increase over 50`() {
            val item = Item(name = AGED_BRIE_NAME, sellIn = 12, quality = MAXIMAL_QUALITY)
            val expectedQuality = item.quality

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(expectedQuality, app.items[0].quality)
        }
    }
}