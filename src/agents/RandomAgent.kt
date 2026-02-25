package agents

import models.Card
import models.Choice
import utils.RandObj

/**
 * An agent that makes decisions based on pure randomness.
 * ### Performance Metrics (n=200) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Performance** | 16.43% |
 * | **95% CI** | [15.26%, 17.59%] |
 * | **Std Deviation** | 8.33% |
 */

class RandomAgent() : Agent() {

    override fun chooseCard(cards: MutableList<Card>): Int {
        if (cards.size == 4) {
            Choice.setChoices(
                mutableListOf(
                    RandObj.nextInt(4) + 1,
                    RandObj.nextInt(3) + 1,
                    RandObj.nextInt(2) + 1
                )
            )
        }
        return Choice.popFirst()
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        return RandObj.nextDouble() < 0.2
    }
}