package com.gildedrose

import kotlin.math.max
import kotlin.math.min

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

internal const val AGED_BRIE_NAME = "Aged Brie"
internal const val BACKSTAGE_PASS_NAME = "Backstage passes to a TAFKAL80ETC concert"
internal const val SULFURAS_NAME = "Sulfuras, Hand of Ragnaros"

private const val CONJURED_PREFIX = "Conjured"

internal val Item.isConjured
    get() = name.startsWith(CONJURED_PREFIX)

internal const val MAXIMAL_QUALITY = 50
internal const val SULFURAS_QUALITY = 80

/** Yeah, this could also be an extension method of some sort. */
internal fun Item.updateAgedBrie() {
    require(name == AGED_BRIE_NAME) {
        "This is a stinky situation: ${name} is not $AGED_BRIE_NAME"
    }

    if (quality < MAXIMAL_QUALITY) quality++
    sellIn--
}

internal fun Item.updateBackstagePass() {
    require(name == BACKSTAGE_PASS_NAME) {
        "You can hear something is off: $name does not sound like $BACKSTAGE_PASS_NAME to me!"
    }

    // The quality of a Backstage pass can not exceed 50
    quality = min(
        MAXIMAL_QUALITY,
        when {
            sellIn <= 0 -> 0
            sellIn <= 5 -> quality + 3
            sellIn <= 10 -> quality + 2
            else -> quality + 1
        }
    )
    sellIn--
}

internal fun Item.updateConjuredItem() {
    require(isConjured) {
        "What's so special about ${name}? That's is not magical!"
    }

    // The quality of an item is non-negative
    quality = max(
        0,
        if (sellIn <= 0) {
            quality - 4
        } else {
            quality - 2
        }
    )
    sellIn--
}

internal fun updateGenericItem(item: Item) {
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