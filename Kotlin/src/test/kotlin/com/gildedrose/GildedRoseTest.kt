package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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

    @Test
    fun `Given an item where the sell in date is passed, Then the quality degrades by two`() {
        val item = Item(name = "we-do-not-care", sellIn = 0, quality = 4)
        val expectedQuality = item.quality - 2

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `The quality of an item can not become negative`() {
        val item = Item(name = "we-do-not-care", sellIn = 12, quality = 0)
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

    @Test
    fun `The quality of Aged Brie increases`() {
        val item = Item(name = "Aged Brie", sellIn = 12, quality = 0)
        val expectedQuality = item.quality + 1

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `The quality of Aged Brie does not increase over 50`() {
        val item = Item(name = "Aged Brie", sellIn = 12, quality = 50)
        val expectedQuality = item.quality

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }


    @Test
    fun `The quality of Sulfuras is consistent`(){
        // Yeah, the quality of this item is not set to 80. Will do so later
        val item = Item(name = "Sulfuras, Hand of Ragnaros", sellIn = 12, quality = 9000)
        val expectedQuality = item.quality

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }
}


