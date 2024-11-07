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
    fun `When we update the quality of Gilded Rose, we update the quality of all its items`() {
        // Given
        val genericItem = Item(name = "we-do-not-care", sellIn = 12, quality = 4)
        val backstagePass = Item(name = BACKSTAGE_PASS_NAME, sellIn = 5, quality = 10)
        val sulfurasItem = Item(name = SULFURAS_NAME, sellIn = 2024, quality = SULFURAS_QUALITY)
        val agedBrie = Item(name = AGED_BRIE_NAME, sellIn = 7, quality = 48)
        val items = listOf(genericItem, backstagePass, sulfurasItem, agedBrie)

        // When
        val gildedRose = GildedRose(items)
        gildedRose.updateQuality()

        // Then
        val expectedItems = listOf(
            genericItem.apply {
                updateGenericItemQuality()
            },
            backstagePass.apply {
                updateBackstagePassQuality()
            },
            sulfurasItem.apply {
                verifySulfuras()
            },
            agedBrie.apply {
                updateAgedBrieQuality()
            },
        )
        // TODO: wait, why does this work again? These are real classes, no data classes. Ah, well, for later
        assertEquals(expectedItems, gildedRose.items)
    }
}
