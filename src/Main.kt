import utils.Constants
import utils.RandObj

fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    val n = 50
    RandObj.init()

    repeat(n) {
        Game(
            'e',
            "random",
            RandObj.nextSeed()
        ).playRun()
    }
}