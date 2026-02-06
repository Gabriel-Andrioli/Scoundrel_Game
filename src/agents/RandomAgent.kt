package agents

import models.Card
import utils.Constants
import kotlin.random.Random

/**
 * An agent that makes decisions based on pure randomness.
 * * ### Performance Metrics (n=200) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Performance** | 16.43% |
 * | **95% CI** | [15.26%, 17.59%] |
 * | **Std Deviation** | 8.33% |
 */

class RandomAgent() : Agent() {

    override fun chooseCard(cards: MutableList<Card>): Int {
        val n = cards.size
        return Constants.RNG.nextInt(1, n+1)
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        return Constants.RNG.nextDouble() < 0.2
    }
}