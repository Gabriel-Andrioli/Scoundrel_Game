package agents

import models.Card
import models.Choice
import utils.Functions
import utils.Constants
import utils.RandObj
import kotlin.math.max

class GreedyAgent() : Agent() {

    override fun chooseCard(cards: MutableList<Card>): Int {
        if (cards.size == 4) {
            val indices = mutableListOf(0, 1, 2, 3)
            val prioritized = indices.sortedByDescending { i ->
                val card = cards[i]
                when (card.suit) {
                    Constants.DIAMONDS -> 100 + card.rank
                    Constants.HEARTHS -> 80 + card.rank
                    else -> 10 - card.rank
                }
            }

            val firstChoice = prioritized[0]
            val secondChoice = prioritized[1]
            val thirdChoice = prioritized[2]

            val firstIndex = firstChoice + 1

            val secondIndex = if (secondChoice > firstChoice) secondChoice else secondChoice + 1

            val thirdIndex = prioritized.subList(0, 2).let { previous ->
                var count = 0
                for (i in 0 until thirdChoice) {
                    if (previous.contains(i)) count++
                }
                (thirdChoice - count) + 1
            }

            Choice.setChoices(
                mutableListOf(
                    firstIndex,
                    secondIndex,
                    thirdIndex
                )
            )
        }
        return Choice.popFirst()
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        val monsterTotal = cards.filter { it.suit == Constants.SPADES || it.suit == Constants.CLUBS }
            .sumOf { it.rank }

        val healingAvailable = cards.filter { it.suit == Constants.HEARTHS }
            .sumOf { it.rank }

        val hasWeapon = cards.any { it.suit == Constants.DIAMONDS }

        return (monsterTotal - healingAvailable > 10) && !hasWeapon
    }
}