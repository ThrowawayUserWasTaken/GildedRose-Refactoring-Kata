package com.gildedrose

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