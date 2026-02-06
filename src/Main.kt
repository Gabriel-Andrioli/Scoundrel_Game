import utils.Constants

fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    Constants.SEED = 2103
    TODO("FIX SEEDED RUN")
    val gamemode = 'e'
    val n = 200

    if (gamemode != null) {
        repeat(n) {
            Game(gamemode, "Random").playRun()
        }
    } else {
        println("Invalid gamemode")
    }
}