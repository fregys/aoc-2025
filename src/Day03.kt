import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/3
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun rec(input: String, target: Int): Long {
        if (input.length <= target) return input.toLong()

        val value = rec(input.substring(1), target)

        var max = value
        for (index in 0..value.toString().length - 1) {
            val removed = value.toString().removeRange(index, index + 1)
            if ((input[0] + removed).toLong() > max) max = (input[0] + removed).toLong()
        }

        return max
    }

    fun part1(filename: String): Long {
        val input = readInput(filename)

        var totalJoltage : Long = 0
        for (line in input) {
            totalJoltage += rec(line, 2)
        }

        return totalJoltage
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)

        var totalJoltage : Long = 0
        for (line in input) {
            totalJoltage += rec(line, 12)
        }

        return totalJoltage
    }

    // Public tests
    require(part1("$folderName/test") == 357.toLong())
    require(part2("$folderName/test") == 3121910778619)

    val result = part2("$folderName/input")
    println(result)
}