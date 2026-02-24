package agents

import models.Card

class MockAgent(): Agent() {
    override fun chooseCard(cards: MutableList<Card>): Int {
        return 0
    }

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        return true
    }

}