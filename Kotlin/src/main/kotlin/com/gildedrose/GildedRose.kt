package com.gildedrose

import kotlin.math.max
import kotlin.math.min

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            if (item.isConjured) {
                updateConjuredItem(item)
            } else {
                when (item.name) {
                    AGED_BRIE_NAME -> updateAgedBrie(item)
                    BACKSTAGE_PASS_NAME -> updateBackstagePass(item)
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

internal const val MAXIMAL_QUALITY = 50
internal const val SULFURAS_QUALITY = 80

/** Yeah, this could also be an extension method of some sort. */
private fun updateAgedBrie(item: Item) {
    require(item.name == AGED_BRIE_NAME) {
        "This is a stinky situation: ${item.name} is not $AGED_BRIE_NAME"
    }

    if (item.quality < MAXIMAL_QUALITY) item.quality++
    item.sellIn--
}

private fun updateBackstagePass(item: Item) {
    require(item.name == BACKSTAGE_PASS_NAME) {
        "You can hear something is off: ${item.name} does not sound like $BACKSTAGE_PASS_NAME to me!"
    }
    val sellIn = item.sellIn
    val quality = item.quality

    // The quality of a Backstage pass can not exceed 50
    item.quality = min(
        MAXIMAL_QUALITY,
        when {
            sellIn <= 0 -> 0
            sellIn <= 5 -> quality + 3
            sellIn <= 10 -> quality + 2
            else -> quality + 1
        }
    )
    item.sellIn--
}

private fun updateConjuredItem(item: Item) {
    require(item.isConjured) {
        "What's so special about ${item.name}? That's is not magical!"
    }

    val sellIn = item.sellIn
    val quality = item.quality

    // The quality of an item is non-negative
    item.quality = max(
        0,
        if (sellIn <= 0) {
            quality - 4
        } else {
            quality - 2
        }
    )

    item.sellIn--
}

private fun updateGenericItem(item: Item) {
    val sellIn = item.sellIn
    val quality = item.quality

    // The quality of an item is non-negative
    item.quality = max(
        0,
        if (sellIn <= 0) {
            quality - 2
        } else {
            quality - 1
        }
    )
    item.sellIn--
}