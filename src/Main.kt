
fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
//    val n=1

    val gamemode = 'e'
    val n = 200

    if (gamemode != null) {
        repeat(n) {
            Game(gamemode, "Human").playRun()
        }
    } else {
        println("Invalid gamemode")
    }
}