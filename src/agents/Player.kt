package agents

import models.Card

/**
 * An agent that makes decisions based on things we don't really know.
 * ### Performance Metrics (n=30000) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Score** | ?? |
 * | **Mean Deepest Room** | ?? |
 * | **Mean Performance** | ??% |
*/

class Player() : Agent() {

    override fun chooseCard(cards: MutableList<Card>, remainingCards: Int): Int {
        print("Choose a card (1-${cards.size}): ")
        return readln().toInt()
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        print("Skip room? (y/n): ")
        val skipRoom = readln().single()
        return skipRoom == 'y'
    }
}