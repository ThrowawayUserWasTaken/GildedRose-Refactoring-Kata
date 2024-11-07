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
                    SULFURAS_NAME -> {
                        /**
                         * Preferably, you would perform in a constructor of a Sulfuras item. But
                         * for reasons explained later, this does not work well here.
                         */
                        check(item.quality == SULFURAS_QUALITY) {
                            "No Sulfuras, Hand of Ragnaros knock-offs with quality ${item.quality} are allowed"
                        }

                        // The Sulfuras, Hand of Ragnaros is pure. Nothing changes
                    }

                    else -> updateGenericItem(item)
                }
            }
        }
    }
}