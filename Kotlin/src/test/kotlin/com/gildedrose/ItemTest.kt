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
}