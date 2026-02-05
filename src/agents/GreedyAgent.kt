package agents

import models.Card
import utils.Functions

class GreedyAgent() : Agent() {
    override fun chooseCard(cards: MutableList<Card>): Int {
        val info = Functions.analyzeCards(this, cards)
        TODO("apply greedy strategy")
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        val info = Functions.analyzeCards(this, cards)
        TODO("apply greedy strategy")
    }
}