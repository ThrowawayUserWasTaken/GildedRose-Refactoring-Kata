package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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

    @Test
    fun `The quality of Sulfuras is always 80`() {
        val item = Item(name = SULFURAS_NAME, sellIn = 12, quality = SULFURAS_QUALITY)
        val expectedQuality = item.quality

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `The sell-in date of Sulfuras does not change`() {
        val item = Item(name = SULFURAS_NAME, sellIn = 12, quality = SULFURAS_QUALITY)
        val expectedSellIn = item.sellIn

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedSellIn, app.items[0].sellIn)
    }

    @Test
    fun `The quality of a backstage pass increases by 1 when the sell-in data is more than 10 days`() {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = 11,
            quality = 10,
        )
        val expectedQuality = item.quality + 1

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [6, 7, 8, 9, 10])
    fun `The quality of a backstage pass increases by 2 when the sell-in data is 10 days or less`(
        sellIn: Int,
    ) {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = sellIn,
            quality = 10,
        )
        val expectedQuality = item.quality + 2

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @Test
    fun `The quality of a backstage pass caps at 50 when the sell-in data is 5 days or less`() {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = 3,
            quality = 48,
        )

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(MAXIMAL_QUALITY, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5])
    fun `The quality of a backstage pass increases by 3 when the sell-in data is 5 days or less`(
        sellIn: Int,
    ) {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = sellIn,
            quality = 10,
        )
        val expectedQuality = item.quality + 3

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(expectedQuality, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [-9000, -3, -2, -1, 0])
    fun `The quality of a backstage pass is zero if the sell-in date is zero or less`(sellIn: Int) {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = sellIn,
            quality = 10,
        )

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(0, app.items[0].quality)
    }

    @Test
    fun `The quality of a backstage pass remains zero if the sell-in date is passed`() {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = -1,
            quality = 0,
        )

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(0, app.items[0].quality)
    }

    @ParameterizedTest
    @ValueSource(ints = [-9000, -3, -2, -1, 0, 5, 23])
    fun `The sell-in date of a backstage pass always moves back by one after the quality update`(
        sellIn: Int
    ) {
        val item = Item(
            name = BACKSTAGE_PASS_NAME,
            sellIn = sellIn,
            quality = 0,
        )

        val items = listOf(item)
        val app = GildedRose(items)
        app.updateQuality()

        assertEquals(sellIn - 1, app.items[0].sellIn)
    }

    @Nested
    @DisplayName("For a conjured item")
    inner class ConjuredItemTest {

        @ParameterizedTest
        @ValueSource(ints = [-9000, -3, -2, -1, 0, 5, 23])
        fun `The sell-in date always decreases by one after a quality update`(sellIn: Int) {
            val item = Item(
                name = CONJURED_ITEM_NAME,
                sellIn = sellIn,
                quality = 0,
            )

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(sellIn - 1, app.items[0].sellIn)
        }

        @ParameterizedTest
        @ValueSource(ints = [-5, 0, 5])
        fun `The quality can not become negative`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 0)
            val expectedQuality = item.quality

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(expectedQuality, app.items[0].quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 12])
        fun `The quality degrades by two if the sell-in date is more than 0`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 8)
            val expectedQuality = item.quality - 2

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(expectedQuality, app.items[0].quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, 0])
        fun `The quality degrades by four if the sell-in date is zero or less`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 8)
            val expectedQuality = item.quality - 4

            val items = listOf(item)
            val app = GildedRose(items)
            app.updateQuality()

            assertEquals(expectedQuality, app.items[0].quality)
        }
    }
}
