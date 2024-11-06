package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            when (item.name) {
                AGED_BRIE_NAME -> {
                    updateAgedBrie(item)
                    continue
                }

                BACKSTAGE_PASS_NAME -> {

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

/** Yeah, this could also be an extension method of some sort. */
private fun updateAgedBrie(item: Item) {
    require(item.name == AGED_BRIE_NAME) {
        "This is a stinky situation: ${item.name} is not $AGED_BRIE_NAME"
    }

    if (item.quality < 50) item.quality++
    item.sellIn--
}
