import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/1
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    val input = readInput("$folderName/input")
    var position = 50
    var secretCode = 0
    val modulo = 100

    for (i in input) {
        val direction = i[0]
        var value = i.drop(1).toInt()

        val turns = value / modulo
        value %= modulo
        secretCode += turns

        val prevPosition = position
        if (direction == 'R') {
            position += value
            position %= modulo
            if (position == 0 || position < prevPosition) {
                secretCode++
            }
        } else {
            position -= value
            if (position == 0 || (position < 0 && prevPosition != 0)) {
                secretCode++
            }
            if (position < 0) {
                position += modulo
            }
        }
    }

    print(secretCode)
}