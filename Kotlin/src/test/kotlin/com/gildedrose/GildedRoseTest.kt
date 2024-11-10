package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun `When we update the quality of Gilded Rose, Then we update the quality of its inventory`() {
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
        assertEquals(expectedItems, gildedRose.items)
    }
}
