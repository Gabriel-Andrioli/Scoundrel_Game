package agents

import models.Card

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
        val monsters = cards.filter { it.suit == "♣" || it.suit == "♠" }
        if (monsters.isEmpty()) return false

        val totalDamage = monsters.sumOf { monster ->
            val canUseWeapon = weapon > 0 && monster.rank < durability
            if (canUseWeapon) maxOf(0, monster.rank - weapon)
            else monster.rank
        }

        return totalDamage > this.hp * 0.6
    }

    override fun chooseCard(cards: MutableList<Card>, remainingCards: Int): Int {
        if (cards.size == 4 || (remainingCards == 0 && cards.size == 2)) {
            plannedCards.clear()

            val available = cards.toMutableList()
            val skip = remainingCards != 0  // cannot pick first card
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

        // 1. Best weapon if better than current
        val bestWeaponIdx = remaining
            .filter { cards[it].suit == "♦" && cards[it].rank > this.weapon }
            .maxByOrNull { cards[it].rank }
        if (bestWeaponIdx != null) {
            order.add(cards[bestWeaponIdx])
            remaining.remove(bestWeaponIdx)
        }

        val weaponAfterPickup = if (bestWeaponIdx != null) cards[bestWeaponIdx].rank else this.weapon
        val durabilityAfterPickup = if (bestWeaponIdx != null) 15 else this.durability

        // 2. Monsters with weapon, strongest first
        val monstersWithWeapon = remaining
            .filter {
                val c = cards[it]
                (c.suit == "♣" || c.suit == "♠") &&
                        weaponAfterPickup > 0 &&
                        c.rank < durabilityAfterPickup
            }
            .sortedByDescending { cards[it].rank }

        monstersWithWeapon.forEach { order.add(cards[it]) }
        remaining.removeAll(monstersWithWeapon.toSet())

        // 3. Potion if not drunk and HP not full
        if (!this.drunk && this.hp < 20) {
            val potionIdx = remaining
                .filter { cards[it].suit == "♥" }
                .maxByOrNull { cards[it].rank }
            if (potionIdx != null) {
                order.add(cards[potionIdx])
                remaining.remove(potionIdx)
            }
        }

        // 4. Remaining monsters weakest first
        val bareMonsters = remaining
            .filter { cards[it].suit == "♣" || cards[it].suit == "♠" }
            .sortedBy { cards[it].rank }

        bareMonsters.forEach { order.add(cards[it]) }
        remaining.removeAll(bareMonsters.toSet())

        // 5. Whatever's left
        remaining.forEach { order.add(cards[it]) }

        return order
    }
}