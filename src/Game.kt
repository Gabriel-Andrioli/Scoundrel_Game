import agents.Agent
import agents.GreedyAgent
import agents.RandomAgent
import models.Card
import models.Deck
import agents.Player
import utils.Constants
import utils.RandObj
import java.io.File

class Game(
    val gamemode : Char,
    val agentType: String = "human",
    seed: Long? = null
)
{
    init {
        if(seed!=null)
            RandObj.init(seed)
        else
            RandObj.init()
    }

    val playerIsHuman: Boolean = (agentType == "greedy")
    val deck: Deck = Deck(gamemode)

    val player: Agent = when(agentType){
        "random" -> RandomAgent()
        "greedy" -> GreedyAgent()
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

    fun saveMetricsToCSV(metrics: Array<String>) {
        val path = "data/${metrics[0]}_agent_metrics.csv"
        val file = File(path)

        file.parentFile?.mkdirs()

        if (!file.exists()) {
            file.writeText(
                "agentType," +
                        "gamemode," +
                        "score," +
                        "deepestRoom," +
                        "finalHP," +
                        "performance," +
                        "seed\n")
        }

        val csvLine = metrics.joinToString(separator = ",")

        file.appendText(csvLine + "\n")
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
            println("SEED: " + Constants.GREEN + RandObj.seed + Constants.RESET)

        saveMetricsToCSV(
            arrayOf(
                this.agentType,
                this.gamemode.toString(),
                (this.player.score).toString(),
                this.room.toString(),
                this.player.hp.toString(),
                performance.toString(),
                RandObj.seed.toString()
            )
        )
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

            choice = player.chooseCard(this.dealed,this.deck.cards.size)
            this.dealed = this.player.interact(this.dealed,choice=choice)
        }

        printGameOver()
    }
}