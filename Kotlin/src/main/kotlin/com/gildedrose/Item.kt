package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

const val AGED_BRIE_NAME = "Aged Brie"
const val BACKSTAGE_PASS_NAME = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS_NAME = "Sulfuras, Hand of Ragnaros"