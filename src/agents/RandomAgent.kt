package agents

import models.Card
import models.Choice
import utils.RandObj

/**
 * An agent that makes decisions based on pure randomness.
 * ### Performance Metrics (n=30000) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Score** | -159.13 |
 * | **Mean Deepest Room** | 4.20 |
 * | **Mean Performance** | 17.10% |
 */

class RandomAgent() : Agent() {

    override fun chooseCard(cards: MutableList<Card>, remainingCards: Int): Int {
        if (cards.size == 4 || (remainingCards == 0 && cards.size == 2)) { // chamber start or last hand
            Choice.clearChoices()
            for (i in 1..cards.size) {
                if (remainingCards != 0 && i == 1)
                    continue
                Choice.appendFirst(RandObj.nextInt(i)+1)
            }
        }
        return Choice.popFirst()
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        return RandObj.nextDouble() < 0.2 // changing this number translates to no statistical improvement
    }
}