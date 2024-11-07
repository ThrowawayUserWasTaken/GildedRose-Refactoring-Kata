package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            if (item.isConjured) {
                item.updateConjuredItemQuality()
            } else {
                when (item.name) {
                    AGED_BRIE_NAME -> item.updateAgedBrieQuality()
                    BACKSTAGE_PASS_NAME -> item.updateBackstagePassQuality()
                    // We only verify Sulfuras, Hand of Ragnaros
                    SULFURAS_NAME -> item.verifySulfuras()
                    else -> item.updateGenericItemQuality()
                }
            }
        }
    }
}