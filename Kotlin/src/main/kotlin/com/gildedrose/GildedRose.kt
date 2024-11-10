package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            val name = item.name
            when {
                item.isConjured -> item.updateConjuredItemQuality()
                name == AGED_BRIE_NAME -> item.updateAgedBrieQuality()
                name == BACKSTAGE_PASS_NAME -> item.updateBackstagePassQuality()
                // We only verify Sulfuras, Hand of Ragnaros
                name == SULFURAS_NAME -> item.verifySulfuras()
                else -> item.updateGenericItemQuality()
            }
        }
    }
}