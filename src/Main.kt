import utils.Constants

fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    val gamemode = 'e'
    val n = 200

    if (gamemode != null) {
        repeat(n) {
            Game(gamemode, "Random").playRun(21032005)
        }
    } else {
        println("Invalid gamemode")
    }
}