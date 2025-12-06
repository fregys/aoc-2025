import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/x
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Int {
        return 0
    }

    fun part2(filename: String): Int {
        return 0
    }

    // Public tests
//    require(part1("$folderName/test") == 0)
//    require(part2("$folderName/test") == 0)

    val result = part1("$folderName/input")
    println(result)
}