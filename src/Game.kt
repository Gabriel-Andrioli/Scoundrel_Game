import agents.Agent
import agents.RandomAgent
import models.Card
import models.Deck
import agents.Player
import utils.Constants

class Game(
    val gamemode : Char,
    agentType: String = "Human"
)
{
    val playerIsHuman: Boolean = (agentType == "Human")
    val deck: Deck = Deck(gamemode)
    val player: Agent = when(agentType){
        "Random" -> RandomAgent()
        else -> Player()
    }
    var room: Int = 1
    var dealed: MutableList<Card> = this.deck.dealUpToN(4)
    var maxScore: Int = when(gamemode) {
        'e' -> 20 + 14 + 13 + 12 + 11
        'm' -> 20 + 14 + 10 + 9 + 8
        else -> 20 + 10 + 9 + 8 + 7
    }

    fun printStatus(){ // for humans
        println("\nHP: " + this.player.hp.toString())
        var weapon: String
        var durability: String

        if(this.player.weapon>0) {
            weapon = this.player.weapon.toString()
            durability = this.player.durability.toString()
        }
        else {
            weapon = "N/A"
            durability = "N/A"
        }
        println("WEAPON: $weapon")
        println("DURABILITY: $durability\n")
        println("SCORE: ${this.player.score}")
    }

    fun printRoom(){
        println("------ROOM: " + this.room + "------")
        for(card in this.dealed){
            card.print()
        }
        printStatus()
        println("CARDS LEFT: " + this.deck.size + "\n")
    }

    fun skipRoom(){
        this.deck.returnToBottom(this.dealed)
        this.dealed = this.deck.dealUpToN(4)
        if(playerIsHuman) printRoom()
    }

    fun calculateFinalScore(){
        this.player.score += this.player.hp
        for(card in this.dealed){
            if(card.suit == "♥")
                this.player.score += card.rank
        }
    }

    fun printGameOver(){
        println("\n".repeat(3))
        println("-----GAME OVER-----")
        calculateFinalScore()

        println("GAMEMODE: " +
                when (this.gamemode) {
                    'e' -> Constants.GREEN + "EASY" + Constants.RESET
                    'm' -> Constants.YELLOW + "MEDIUM" + Constants.RESET
                    else -> Constants.RED + "HARD" + Constants.RESET
                }
        )
        println("FINAL SCORE: " + Constants.BLUE + (this.player.score).toString() + Constants.RESET)
        println("DEEPEST ROOM LEVEL: " + this.room)
        println("FINAL HP: " + this.player.hp +
                if(this.player.hp>0)
                    " ---> YOU MADE IT!"
                else " ---> TOO BAD...")

        println("MAX POSSIBLE SCORES:")
        println(Constants.GREEN + " 70 - EASY (ALL CARDS)" + Constants.RESET)
        println(Constants.YELLOW + " 61 - MEDIUM (NO RED FACES)" + Constants.RESET)
        println(Constants.RED + " 54 - HARD (RED HAS ONLY NUMBERS)" + Constants.RESET)

        val performance = (100*(this.player.score+208)/(this.maxScore+208))
        println("PERFORMANCE: $performance%")

        if (player is RandomAgent)
            println("SEED: " + Constants.GREEN + Constants.SEED + Constants.RESET)
    }

    fun playRun() {
        var skipRoom = false
        var choice: Int
        
        while((this.player.hp > 0) and (this.player.score < 0)){
            if(this.dealed.size==1){
                this.dealed += this.deck.dealUpToN(3)
                this.room++
                this.player.drunk = false
            }
            if(playerIsHuman) printRoom()

            if(this.dealed.size==4)
                skipRoom = player.chooseSkip(this.dealed)

            if(skipRoom) skipRoom()
            skipRoom = false

            choice = player.chooseCard(this.dealed)
            this.dealed = this.player.interact(this.dealed,choice=choice)
        }

        printGameOver()
    }
}