package models

class Room(
    val cards: List<Card>,
    val playerIsAgent: Boolean
) {
    fun printRoom(){
        if (!playerIsAgent)
            for(card in this.cards){
                card.print()
            }
        else {

        }
    }
}