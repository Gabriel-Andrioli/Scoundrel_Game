package agents

import models.Card
import utils.Functions
import kotlin.math.max

class GreedyAgent() : Agent() {
    override fun chooseCard(cards: MutableList<Card>): Int {
        val info = Functions.analyzeCards(this, cards)
        return 1
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        val info = Functions.analyzeCards(this, cards)
        return false
    }
}