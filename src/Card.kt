class Card(
    val suit: String,
    val rank: Int
)
{
    fun print(){
        var rank = this.rank.toString()
        when(rank){
            "11" -> rank = "JACK"
            "12" -> rank = "QUEEN"
            "13" -> rank = "KING"
            "14" -> rank = "ACE"
        }

        val color = when(this.suit) {
            "♥" -> Constants.RED
            "♦" -> Constants.GREEN
            else -> Constants.BLACK
        }

        print(color + rank + "-" + this.suit + " " + Constants.RESET)
    }
}