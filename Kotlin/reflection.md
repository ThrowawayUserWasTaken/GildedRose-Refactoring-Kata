# Reflection on the Gilded Rose Kata

I liked this exercise! It really makes it easy to show off TDD skills and it's easy enough to
actually accomplish something in a relatively short time span.

Here, I will enlist some choices I made that may not be clear from code (which would be a shame
IMO), and [improvements](#future-improvements) I would do if time was not a factor. If you have
questions or anything, feel free to bring these up during our interview on Tuesday.

## The Gilded Rose can sell conjured items now!

When we update the Gilded Rose inventory, we update the quality of all of the items. However, you
might spot that the Sulfuras, Hand of Ragnaros will not update (as per the requirements) – we just
verify we only sell real Sulfurases. This might safe them from a hefty Belastingdienst fine, or they
might go under because the cannot sell knock-offs anymore. We'll have to wait and see.

### Rationale

The `Item` class is not extended (the reasons are laid out down below), but we created extension
methods to update the quality of the specific items. I like extension methods
(`Item.updateAgedBrie()`) over "normal" functions (`updateAgedBrie(item: Item)`), as they do not
clutter the IDE as much.

More about scoping: as you follow along, you can see that the tests become way more scoped, as we do
not have to create a full app the test the quality update for an item. That's improvement!

You could argue that the different extension methods have some overlap. All of them (except for the
Sulfuras one) end with decreasing the sell-in date, which also means you have to test all of them (I
think I might've skipped one or two, whoops!). I allowed this, because it's easier to see what's
happening. You could update the quality and the sell-in date separately, by just moving decreasing
the sell-in date to a separate method, but then you had to make an exception for a Sulfuras. In this
way, all "business logic" is contained in separate methods, with some duplication, so we trade off
duplication with clarity. To me, that is a trade-off worth taking.

Moreover, if for example, the Gilded Rose were to have certain "spoiling items", where the sell-in
date would decrease twice as fast, then we would have to update the decrease method, instead of
creating a new `Item.updateSpoiledItem()` method.

As you can see in the [log](log.md#reflection) file and from the Git history, I like to commit small
items. Just rebase or squash afterwards!

### Future improvements

I'm somewhat satisfied with the work, but there are a lot of things I would like to change, but
feel would be overkill for this exercise. I'll enlist them below for you to see my reasoning behind
them.

#### Make the items immutable, and move them to a separate module

One of my main gripes of this exercise was the `Item` class. It's declared as `open`, so you could
maybe use it to override it with specific classes, to create an `AgedBrie` or a `Sulfuras` class.
However, I tried, but it meant I had to update the class, which was against the rules. I tried, but
reverted the changes in #9c50b04, as not to offend the corner goblin.

If I could do things differently, it might end up looking like this.

```kotlin
sealed interface Item {
    val name: String
    val sellIn: SellIn
    val quality: Int
}

// This is maybe a bit overkill, but just wanted to say that value classes are nice to use sometimes
@JvmInline
value class SellIn(val value: Int)

data class Sulfuras(
    override val sellIn: SellIn,
) : Item {
    override val name: String = SULFURAS_NAME
    override val quality: Int = SULFURAS_QUALITY
}

data class AgedBrie(
    override val sellIn: SellIn,
    override val quality: Int,
) : Item {
    override val name: String = AGED_BRIE_NAME

    init {
        require(quality in 0..MAXIMAL_QUALITY)
    }
}

data class BackstagePass(
    override val sellIn: SellIn,
    override val quality: Int,
) : Item {
    override val name: String = BACKSTAGE_PASS_NAME

    init {
        require(quality in 0..MAXIMAL_QUALITY)
    }
}

data class Generic(
    override val name: String,
    override val sellIn: SellIn,
    override val quality: Int,
) : Item {

    init {
        require(quality in 0..MAXIMAL_QUALITY)
    }
}
```

These classes would live in their own separate domain module in the main project.

##### Benefits

We could update the now existing `Item.updateAgedBrie()` to a more scoped extension method
`AgedBrie.updateQuality()` (but you can not readily mutate the quality, see below). That way, we
make it easier to avoid mistakes.

###### For testing

- All the classes are immutable data classes, so test comparisons are a bit easier to do.
- The quality assertion for Sulfurares is not necessary anymore, as it has a pre-determined quality.
- In similar vein, all the name checks for the extension methods are redundant.

##### Mutations

Mutating these items would be done in a use-case, and would return a new instance of the item.
Something like this.

```kotlin
interface UpdateQuality<T : Item> {
    operator fun invoke(item: T): T
}

class UpdateAgedBrieQuality : UpdateQuality<AgedBrie> {
    override fun invoke(item: AgedBrie): AgedBrie {
        TODO("Not yet implemented")
    }
}
```

But this could also have its downsides. Just know I gave it a thought!
