package com.gildedrose

import kotlin.math.min

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            when (item.name) {
                AGED_BRIE_NAME -> {
                    updateAgedBrie(item)
                    continue
                }

                BACKSTAGE_PASS_NAME -> {
                    updateBackstagePass(item)
                    continue
                }

                SULFURAS_NAME -> {

                }

                else -> {

                }
            }

            if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
                if (item.quality > 0) {
                    if (item.name != "Sulfuras, Hand of Ragnaros") {
                        item.quality -= 1
                    }
                }
            } else {
                if (item.quality < 50) {
                    item.quality += 1

                    if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality += 1
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality += 1
                            }
                        }
                    }
                }
            }

            if (item.name != "Sulfuras, Hand of Ragnaros") {
                item.sellIn -= 1
            }

            if (item.sellIn < 0) {
                if (item.name != "Aged Brie") {
                    if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.quality > 0) {
                            if (item.name != "Sulfuras, Hand of Ragnaros") {
                                item.quality -= 1
                            }
                        }
                    } else {
                        item.quality = 0
                    }
                } else {
                    if (item.quality < 50) {
                        item.quality += 1
                    }
                }
            }
        }
    }
}

internal const val MAXIMAL_QUALITY = 50

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
}