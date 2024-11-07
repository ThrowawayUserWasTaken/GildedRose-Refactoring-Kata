package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ItemTest {

    @Nested
    @DisplayName("For a conjured item")
    inner class ConjuredItemTest {

        @Test
        fun `It is conjured if its name starts with Conjured`() {
            val item = Item(name = "Conjured Mountain Dew", sellIn = 900, quality = 21)
            assertTrue(item.isConjured)
        }

        @Test
        fun `It is not conjured if its name does not start with Conjured`() {
            val item = Item(name = "Mountain Dew", sellIn = 900, quality = 20)
            assertFalse(item.isConjured)
        }

        @ParameterizedTest
        @ValueSource(ints = [-9000, -3, -2, -1, 0, 5, 23])
        fun `The sell-in date always decreases by one after a quality update`(sellIn: Int) {
            val item = Item(
                name = CONJURED_ITEM_NAME,
                sellIn = sellIn,
                quality = 0,
            )

            item.updateConjuredItem()

            assertEquals(sellIn - 1, item.sellIn)
        }

        @ParameterizedTest
        @ValueSource(ints = [-5, 0, 5])
        fun `The quality can not become negative`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 0)
            val expectedQuality = item.quality

            item.updateConjuredItem()

            assertEquals(expectedQuality, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 12])
        fun `The quality degrades by two if the sell-in date is more than 0`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 8)
            val expectedQuality = item.quality - 2

            item.updateConjuredItem()

            assertEquals(expectedQuality, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, 0])
        fun `The quality degrades by four if the sell-in date is zero or less`(sellIn: Int) {
            val item = Item(name = CONJURED_ITEM_NAME, sellIn = sellIn, quality = 8)
            val expectedQuality = item.quality - 4

            item.updateConjuredItem()

            assertEquals(expectedQuality, item.quality)
        }
    }

    @Nested
    @DisplayName("For Aged Brie")
    inner class AgedBrieTest {

        @Test
        fun `The quality increases`() {
            val item = Item(name = AGED_BRIE_NAME, sellIn = 12, quality = 0)
            val expectedQuality = item.quality + 1

            item.updateAgedBrie()

            assertEquals(expectedQuality, item.quality)
        }

        @Test
        fun `The quality does not increase over 50`() {
            val item = Item(name = AGED_BRIE_NAME, sellIn = 12, quality = MAXIMAL_QUALITY)
            val expectedQuality = item.quality

            item.updateAgedBrie()

            assertEquals(expectedQuality, item.quality)
        }

        @Test
        fun `It will only handle aged brie`() {
            val name = "Ceci n'est pas Aged Brie"
            val item = Item(
                name = name,
                sellIn = 12,
                quality = MAXIMAL_QUALITY,
            )

            val exception = assertThrows<IllegalArgumentException> {
                item.updateAgedBrie()
            }

            /**
             * Yes, there is some code duplication. You could always extract the error message to a
             * separate value or method. However, for this kata, this suffices.
             */
            assertEquals(
                "This is a stinky situation: $name is not $AGED_BRIE_NAME",
                exception.message
            )
        }
    }

    @Nested
    @DisplayName("For backstage passes")
    inner class BackstagePassTest {

        @Test
        fun `The quality increases by 1 when the sell-in data is more than 10 days`() {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = 11,
                quality = 10,
            )
            val expectedQuality = item.quality + 1

            item.updateBackstagePass()

            assertEquals(expectedQuality, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [6, 7, 8, 9, 10])
        fun `The quality increases by 2 when the sell-in data is 10 days or less`(
            sellIn: Int,
        ) {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = sellIn,
                quality = 10,
            )
            val expectedQuality = item.quality + 2

            item.updateBackstagePass()

            assertEquals(expectedQuality, item.quality)
        }

        @Test
        fun `The quality caps at 50`() {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = 3,
                quality = 48,
            )

            item.updateBackstagePass()

            assertEquals(MAXIMAL_QUALITY, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 2, 3, 4, 5])
        fun `The quality increases by 3 when the sell-in data is 5 days or less`(sellIn: Int) {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = sellIn,
                quality = 10,
            )
            val expectedQuality = item.quality + 3

            item.updateBackstagePass()

            assertEquals(expectedQuality, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [-9000, -3, -2, -1, 0])
        fun `The quality is zero if the sell-in date is zero or less`(sellIn: Int) {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = sellIn,
                quality = 10,
            )

            item.updateBackstagePass()

            assertEquals(0, item.quality)
        }

        @Test
        fun `The quality remains zero if the sell-in date is passed`() {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = -1,
                quality = 0,
            )

            item.updateBackstagePass()

            assertEquals(0, item.quality)
        }

        @ParameterizedTest
        @ValueSource(ints = [-9000, -3, -2, -1, 0, 5, 23])
        fun `The sell-in date always moves back by one after the quality update`(sellIn: Int) {
            val item = Item(
                name = BACKSTAGE_PASS_NAME,
                sellIn = sellIn,
                quality = 0,
            )

            item.updateBackstagePass()

            assertEquals(sellIn - 1, item.sellIn)
        }

        @Test
        fun `Can only handle backstage passes`() {
            val name = "Frontstage pass to a TAFKAL80ETC concert"
            val item = Item(
                name = name,
                sellIn = 11,
                quality = 10,
            )

            val exception = assertThrows<IllegalArgumentException> {
                item.updateBackstagePass()
            }

            assertEquals(
                "You can hear something is off: $name does not sound like $BACKSTAGE_PASS_NAME to me!",
                exception.message
            )
        }
    }

    @Nested
    @DisplayName("For Sulfuras, Hand of Ragnaros")
    inner class SulfurasTest {

        @Test
        fun `The quality is always 80 after updating the quality`() {
            val item = Item(name = SULFURAS_NAME, sellIn = 12, quality = SULFURAS_QUALITY)
            val expectedQuality = item.quality

            item.verifySulfuras()

            assertEquals(expectedQuality, item.quality)
        }

        @Test
        fun `The name is very specific`() {
            val name = "Sultana, Hand of Ragnarok"
            val item = Item(name = name, sellIn = 12, quality = SULFURAS_QUALITY)

            val exception = assertThrows<IllegalStateException> {
                item.verifySulfuras()
            }

            assertEquals(
                "That's weird, this item ($name) is not $SULFURAS_NAME",
                exception.message,
            )
        }

        @Test
        fun `The quality should be 80`() {
            val nonSulfurasQuality = SULFURAS_QUALITY - 1
            val item = Item(name = SULFURAS_NAME, sellIn = 12, quality = nonSulfurasQuality)

            val exception = assertThrows<IllegalStateException> {
                item.verifySulfuras()
            }

            assertEquals(
                "No Sulfuras, Hand of Ragnaros knock-offs with quality $nonSulfurasQuality are allowed",
                exception.message,
            )
        }

        @Test
        fun `The sell-in date does not change`() {
            val item = Item(name = SULFURAS_NAME, sellIn = 12, quality = SULFURAS_QUALITY)
            val expectedSellIn = item.sellIn

            item.verifySulfuras()

            assertEquals(expectedSellIn, item.sellIn)
        }
    }

    @Nested
    @DisplayName("For generic items")
    inner class GenericItemTest {

        @Test
        fun `The quality degrades by one if sell-in date passes in the future`() {
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
        fun `The quality degrades by two when the sell-in date is zero or less`(
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
        fun `The quality can not become negative`(sellIn: Int) {
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
}