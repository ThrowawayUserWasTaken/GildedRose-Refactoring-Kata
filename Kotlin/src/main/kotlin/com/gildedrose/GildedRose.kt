package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            if (item.isConjured) {
                item.updateConjuredItem()
            } else {
                when (item.name) {
                    AGED_BRIE_NAME -> item.updateAgedBrie()
                    BACKSTAGE_PASS_NAME -> item.updateBackstagePass()
                    // We only verify Sulfuras, Hand of Ragnaros
                    SULFURAS_NAME -> item.verifySulfuras()
                    else -> item.updateGenericItemQuality()
                }
            }
        }
    }
}