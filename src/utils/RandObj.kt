package utils

import kotlin.random.Random

object RandObj {

    var seed: Long = System.currentTimeMillis()
        private set

    var rng: Random = Random.Default
        private set

    fun init(newSeed: Long? = null) {
        val finalSeed = newSeed ?: System.currentTimeMillis()
        this.seed = finalSeed
        this.rng = Random(finalSeed)
    }

    fun nextInt(until: Int) = rng.nextInt(until)
    fun nextSeed() = nextInt(100000).toLong()
    fun nextDouble() = rng.nextDouble()
    fun nextBoolean() = rng.nextBoolean()
}