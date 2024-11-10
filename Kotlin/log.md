# Log to keep some notes.

- Just noticed the "./gradlew -q text" command in the README does not work. Won't fix that. Will write my own tests to fix that.

## Approach
Every task defined in the [requirements](https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md)
should be a separate branch, so every PR could run the pipelines to assume quality. For now, that's 
totally overkill.

## Reflection
I like small commits. I want every commit to be a working state of the app. Add tests first, then 
fix the code, then refactor. We will take that approach here.

## Notes
Okay, we have verified that Leeroy did a great job. He fixed the first five requirements by adding 
tests for all these scenarios.
- Once the sell by date has passed, Quality degrades twice as fast
- The Quality of an item is never negative
- "Aged Brie" actually increases in Quality the older it gets
- The Quality of an item is never more than 50
- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality

Having added all the related tests, we can actually start to refactor some of his code.

Whoops, forget the other ones did too! Reading is hard apparently.

Final note for today. Just spent 4 pomodori working on this. It was fun. Will add conjured items later. See ya!

# Notes for November 7th
After some coding, my MacBook died. Luckily, the commits are very small and in sync with remote, so
it would not have been a big issue. That's the benefit of small commits.

For context, my approach to changes is: commit quick little chunks. Rebasing or squashing (my preference) 
can always be done after.

## To do for now
[x] Enforce 80 quality on Sulfarases
[x] Replace all names of items with the defined constants
[x] Extract quality update to make Gilded Rose test easier to read
    [x] `updateBackstagePass`
    [x] `updateGenericItem`
[ ] Finalize everything with a reflection on this exercise
[ ] Add docs to quality. It is between 0 and 50, except for certain items

Time log: 2:00 today

## Now what?
There are shared properties for all items, and for almost all items, the sell-in date decreases by 
one after updating the quality. The way I've set it up now, all items have their own quality update,
which you could argue is overkill. But I think it's better, and I have my reasoning.

// TODO: update this reasoning a bit more
### The business rules are defined for specific items
The decreasing of the sell-in date could maybe be done for all items in one method. However, you 
mustn't forget the Sulfuras is pure and so its sell-in data does not decrease. You could always
account for that in a shared `decreaseSellIn()` method and test accordingly.

However, you also need to take the sell-in date into account for updating the quality of an item. So
having a separate sell-in method would be an afterthought.

# Nov 10th
To-do list:
[ ] Finish to-dos from Nov 7th 
[ ] Add "future improvements"
[ ] Add reflection
