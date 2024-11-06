package com.gildedrose

// TODO: revert the "open" modifiers? Sorry, corner goblins!
open class Item(var name: String, open var sellIn: Int, open var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

const val AGED_BRIE_NAME = "Aged Brie"

class AgedBrie(override var sellIn: Int, override var quality: Int) :
    Item(name = AGED_BRIE_NAME, sellIn = sellIn, quality = quality)