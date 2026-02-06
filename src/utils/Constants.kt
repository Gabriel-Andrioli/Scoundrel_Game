package utils

import kotlin.random.Random

object Constants {
    const val RED = "\u001b[31m"
    const val GREEN = "\u001b[32m"
    const val BLACK = "\u001b[30m"
    const val YELLOW = "\u001b[33m"
    const val BLUE = "\u001b[34m"

    const val RESET = "\u001b[0m"

    var SEED: Long = 21032005
    var RNG: Random = if(SEED.toInt() == 21032005) Random.Default else Random(SEED)
}