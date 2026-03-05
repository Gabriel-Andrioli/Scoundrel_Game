import utils.Constants
import utils.RandObj
import kotlin.math.pow
import kotlin.random.Random
import utils.Functions

fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    val n = 1
    val rng = Random(System.currentTimeMillis())
    val rands = (1..n).map { rng.nextLong(0,Long.MAX_VALUE) }

    for(rand in rands) {
        Game(
            'e',
            "greedy",
            rand
        ).playRun()
    }
}