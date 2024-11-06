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
