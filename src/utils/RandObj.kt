package utils

import kotlin.random.Random

object RandObj {

    var rng: Random = Random.Default
    var seed: Long = Random.nextLong()

    fun init(argSeed: Long? = null) {
        if (argSeed != null)
            seed = argSeed
        rng = Random(seed)
    }
}