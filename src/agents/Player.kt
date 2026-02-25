package agents

import models.Card

/**
 * An agent that makes decisions based on things we don't really know.
 * ### Performance Metrics (n=200) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Performance** | ??% |
 * | **95% CI** | [??%, ??%] |
 * | **Std Deviation** | ??% |
 */

class Player() : Agent() {

    override fun chooseCard(cards: MutableList<Card>): Int {
        print("Choose a card (1-${cards.size}): ")
        return readln().toInt()
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        print("Skip room? (y/n): ")
        val skipRoom = readln().single()
        return skipRoom == 'y'
    }
}