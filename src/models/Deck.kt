package models

import utils.Constants
import kotlin.random.Random

class Deck(
    val gamemode: Char
)
{
    var cards: MutableList<Card> = mutableListOf()
    var size: Int = 0
    val suits = arrayOf("♥", "♦", "♣", "♠")
    var ranks = 2..14

    val hardList = arrayOf(
        "♥11","♥12","♥13", "♥14",
        "♦11","♦12","♦13","♦14"
    ) // only red numbers

    val mediumList = arrayOf(
        "♥11","♥12","♥13",
        "♦11","♦12","♦13"
    ) // no red royal family

    val gamemodeList: Array<String> = when(this.gamemode){
        'h' -> hardList
        'm' -> mediumList
        else -> emptyArray()
    }

    init {
        for (suit in suits)
            for (rank in ranks)
                if (suit + rank.toString() !in gamemodeList)
                cards.add(Card(suit, rank))

        this.cards.shuffle(Constants.RNG)
        this.size = this.cards.size
    }

    fun dealUpToN(n : Int) : MutableList<Card>{
        val dealed: MutableList<Card> = mutableListOf()
        for (i in 0..<n) {
            if (this.size == 0)
                return dealed
            else {
                dealed.add(this.cards.removeFirst())
                this.size -= 1
            }
        }
        return dealed
    }

    fun returnToBottom(cards: MutableList<Card>){
        this.cards += cards
        this.size += cards.size
    }
}