import utils.Constants
import utils.RandObj

fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    val n = 200
    RandObj.init()

    repeat(n) {
        Game(
            'e',
            "Random",
            RandObj.nextSeed()
        ).playRun()
    }
}