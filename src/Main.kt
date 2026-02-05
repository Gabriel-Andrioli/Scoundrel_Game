fun main() {
//    print("Choose gamemode (e/m/h): ")
//    val gamemode = readLine()?.single()
    val gamemode = 'e'

    if (gamemode != null) {
        repeat(200) {
            Game(gamemode, "Human").playRun()
        }
    } else {
        println("Invalid gamemode")
    }
}