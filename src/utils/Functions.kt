package utils

import agents.Agent
import models.Card
import kotlin.math.max

class Functions {
    companion object {

        data class RoomAnalysis(
            val totalEnemies: Int,
            val maxEnemy: Int,
            val totalHealthPotential: Int,
            val maxWeaponPotential: Int
        )

        fun analyzeCards(player: Agent, cards: List<Card>): RoomAnalysis {
            var totalEnemies = 0
            var maxEnemy = 0
            var totalHealth = player.hp
            var maxWeapon = player.weapon

            for (card in cards) {
                when (card.suit) {
                    "♣", "♠" -> {
                        totalEnemies += card.rank
                        maxEnemy = max(card.rank, maxEnemy)
                    }
                    "♥" -> totalHealth += card.rank

                    else -> maxWeapon = max(card.rank, maxWeapon)
                }
            }

            return RoomAnalysis(
                totalEnemies = totalEnemies,
                maxEnemy = maxEnemy,
                totalHealthPotential = totalHealth,
                maxWeaponPotential = maxWeapon
            )
        }
    }

}