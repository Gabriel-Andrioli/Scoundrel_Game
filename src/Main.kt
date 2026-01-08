
fun main() {
    print("Choose gamemode (e/m/h): ")
    val gamemode = readLine()?.single()
    if(gamemode != null)
        Game(gamemode).playRun()
    else
        println("Invalid gamemode")
}