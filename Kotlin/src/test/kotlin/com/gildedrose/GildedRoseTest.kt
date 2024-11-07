package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = listOf(TEST_ITEM)
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(TEST_ITEM_NAME, app.items[0].name)
    }

    @Test
    fun `Given an item where the sell in date passes in the future, Then the quality degrades by one`() {
        val item = Item(name = "we-do-not-care", sellIn = 12, quality = 4)
        val expectedQuality = item.quality - 1

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `Given an item where the sell in date passes today, Then the quality degrades by one`() {
        val item = Item(name = "we-do-not-care", sellIn = 1, quality = 4)
        val expectedQuality = item.quality - 1

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [-9000, -3, -2, -1, 0])
    fun `Given an item where the sell in date is zero or less, Then the quality degrades by two`(
        sellIn: Int
    ) {
        val item = Item(name = "we-do-not-care", sellIn = sellIn, quality = 4)
        val expectedQuality = item.quality - 2

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [-9000, -3, -2, -1, 0, 4, 8, 12, 99])
    fun `The quality of an item can not become negative`(sellIn: Int) {
        val item = Item(name = "we-do-not-care", sellIn = sellIn, quality = 0)
        val expectedQuality = item.quality

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `The sell-in date always decreases by one when the quality is updated`() {
        val item = Item(name = "we-do-not-care", sellIn = 12, quality = 0)
        val expectedSellIn = item.sellIn - 1

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedSellIn, app.items[0].sellIn)
    }
}
