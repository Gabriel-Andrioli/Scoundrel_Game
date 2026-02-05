package agents

import models.Card
import kotlin.random.Random

/**
 * An agent that makes decisions based on pure randomness.
 * * ### Performance Metrics (n=200) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Performance** | 16.43% |
 * | **95% CI** | [15.26%, 17.59%] |
 * | **Std Deviation** | 8.33% |
 *
 * @property seed The specific Long used to initialize the [rng].
 * Providing the same seed ensures deterministic behavior for replays.
 */
class RandomAgent(
    val seed: Long = Random.Default.nextLong()
) : Agent() {

    private val rng = Random(seed)

    override fun chooseCard(cards: MutableList<Card>): Int {
        val n = cards.size
        return rng.nextInt(1, n+1)
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        return rng.nextDouble() < 0.2
    }
}