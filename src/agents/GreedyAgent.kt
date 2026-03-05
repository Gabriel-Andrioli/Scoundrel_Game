package agents

import models.Card
import utils.Constants
import utils.Functions

/**
 * An agent that makes decisions based on greedy instant decisions.
 * ### Performance Metrics (n=30000) (Easy mode)
 * | Metric | Value |
 * | :--- | :--- |
 * | **Mean Score** | -117.62 |
 * | **Mean Deepest Room** | 8.55 |
 * | **Mean Performance** | 32.02% |
 */


class GreedyAgent : Agent() {

    private val plannedCards = mutableListOf<Card>()

    override fun chooseSkip(cards: MutableList<Card>): Boolean {
        val monsters = cards.filter { it.suit == Constants.CLUBS || it.suit == Constants.SPADES }
        if (monsters.isEmpty()) return false

        // Calculate real damage considering weapon and decreasing durability
        var simDurability = this.durability
        val sortedMonsters = monsters.sortedByDescending { it.rank }
        val realDamage = sortedMonsters.sumOf { monster ->
            val dmg = if (weapon > 0 && monster.rank < simDurability)
                maxOf(0, monster.rank - weapon)
            else
                monster.rank
            simDurability = if (weapon > 0 && monster.rank < simDurability) monster.rank else simDurability
            dmg
        }

        // Factor in potions: they can recover HP
        val potionValue = if (!drunk)
            cards.filter { it.suit == Constants.HEARTS }.maxOfOrNull { it.rank } ?: 0
        else 0

        val netDamage = realDamage - potionValue
        return netDamage > hp * 0.5
    }

    override fun chooseCard(cards: MutableList<Card>, remainingCards: Int): Int {
        if (cards.size == 4 || (remainingCards == 0 && cards.size == 2)) {
            plannedCards.clear()

            val available = cards.toMutableList()
            val skip = remainingCards != 0
            val indices = if (skip) (1 until available.size).toMutableList()
            else (0 until available.size).toMutableList()

            val order = decideOrder(available, indices)
            plannedCards.addAll(order)
        }

        val nextCard = plannedCards.removeFirst()
        return cards.indexOf(nextCard) + 1
    }

    private fun decideOrder(cards: MutableList<Card>, indices: MutableList<Int>): List<Card> {
        val remaining = indices.toMutableList()
        val order = mutableListOf<Card>()

        var simWeapon = this.weapon
        var simDurability = this.durability
        var simDrunk = this.drunk
        var simHp = this.hp

        // 1. Best weapon — only upgrade if new weapon is stronger AND
        // current weapon is exhausted/absent or new weapon exceeds current durability cap
        val bestWeaponIdx = remaining
            .filter { cards[it].suit == Constants.DIAMONDS }
            .filter { cards[it].rank > simWeapon }
            .filter { simWeapon == 0 || cards[it].rank > simDurability }
            .maxByOrNull { cards[it].rank }
        if (bestWeaponIdx != null) {
            order.add(cards[bestWeaponIdx])
            remaining.remove(bestWeaponIdx)
            simWeapon = cards[bestWeaponIdx].rank
            simDurability = 15
        }

        // 2. Collect monsters with weapon (strongest first) and bare monsters (weakest first)
        val weaponMonsters = remaining
            .filter {
                val c = cards[it]
                (c.suit == Constants.CLUBS || c.suit == Constants.SPADES) &&
                        simWeapon > 0 && c.rank < simDurability
            }
            .sortedByDescending { cards[it].rank }

        val bareMonsters = remaining
            .filter {
                val c = cards[it]
                (c.suit == Constants.CLUBS || c.suit == Constants.SPADES) &&
                        !(simWeapon > 0 && c.rank < simDurability)
            }
            .sortedBy { cards[it].rank }

        val potionIdx = if (!simDrunk)
            remaining.filter { cards[it].suit == Constants.HEARTS }.maxByOrNull { cards[it].rank }
        else null

        // Simulate damage from all monsters to find optimal potion placement
        if (potionIdx != null) {
            var tempHp = simHp
            var tempDurability = simDurability
            val monsterSequence = weaponMonsters + bareMonsters

            // Find where HP would drop low enough to warrant early healing
            var insertPotionBefore: Int? = null  // index in monsterSequence
            for (i in monsterSequence.indices) {
                val m = cards[monsterSequence[i]]
                val dmg = if (simWeapon > 0 && m.rank < tempDurability)
                    maxOf(0, m.rank - simWeapon)
                else m.rank
                tempDurability = if (simWeapon > 0 && m.rank < tempDurability) m.rank else tempDurability

                if (tempHp - dmg <= 0) {
                    // Would die — insert potion before this monster
                    insertPotionBefore = i
                    break
                }
                tempHp -= dmg
            }

            val potionCard = cards[potionIdx]
            val potionHeal = potionCard.rank

            if (insertPotionBefore != null) {
                // Use potion before the lethal monster
                for (i in monsterSequence.indices) {
                    if (i == insertPotionBefore) {
                        if (simHp < 20) { order.add(potionCard); simDrunk = true }
                    }
                    order.add(cards[monsterSequence[i]])
                }
            } else {
                // Safe to take all damage first — use potion last to maximize healing
                val hpAfterAll = tempHp
                if (hpAfterAll < 20) {
                    // Potion has value — use after all monsters
                    monsterSequence.forEach { order.add(cards[it]) }
                    order.add(potionCard)
                    simDrunk = true
                } else {
                    // HP still full after all damage — potion wasted, skip it
                    monsterSequence.forEach { order.add(cards[it]) }
                }
            }

            remaining.remove(potionIdx)
            remaining.removeAll(weaponMonsters.toSet())
            remaining.removeAll(bareMonsters.toSet())
        } else {
            // No potion — just add monsters in order
            for (idx in weaponMonsters) {
                order.add(cards[idx])
                remaining.remove(idx)
            }
            bareMonsters.forEach { order.add(cards[it]) }
            remaining.removeAll(bareMonsters.toSet())
        }

        // 5. Whatever's left (useless potions, inferior weapons, etc.)
        remaining.forEach { order.add(cards[it]) }

        return order
    }
}