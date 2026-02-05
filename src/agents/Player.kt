package agents

import models.Card

class Player() : Agent()
{
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