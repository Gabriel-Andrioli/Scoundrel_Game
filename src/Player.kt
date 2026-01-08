class Player()
{
    var hp: Int = 20
    var drunk: Boolean = false
    var score: Int = -208
    var weapon: Int = 0
    var durability: Int = 0

    fun interact(cards: MutableList<Card>, choice: Int): MutableList<Card>{
        val choosenCard = cards[choice-1]

        when(choosenCard.suit){
            "♥" -> { // potion
                if (!this.drunk) {
                    this.hp =
                        if(choosenCard.rank + this.hp < 20)
                            choosenCard.rank + this.hp
                        else 20
                    this.drunk = true
                }
            }
            "♦" -> { // weapon
                this.weapon = choosenCard.rank
                this.durability = 14
            }

            else -> { // monster
                if(choosenCard.rank < this.durability){
                    this.hp -=
                        if(choosenCard.rank > this.weapon)
                            choosenCard.rank - this.weapon
                        else 0
                }
                else
                    this.hp -= choosenCard.rank

                if(this.weapon > 0) {
                    this.durability =
                        if(choosenCard.rank < this.durability)
                            choosenCard.rank
                        else this.durability
                }

                this.score += choosenCard.rank
            }
        }

        cards.remove(choosenCard)
        return cards
    }
}
