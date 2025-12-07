import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/7
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Int {
        val input = readInput(filename).toMutableList()
        var columnPositions: MutableList<Int> = mutableListOf()

        columnPositions.add(input[0].indexOf("S"))
        input.removeFirst()

        var totalSplits = 0
        for (line in input) {
            val newColumnPositions: MutableSet<Int> = mutableSetOf()
            for(column in columnPositions) {
                if (line[column] == '^') {
                    for (shift in listOf(-1, 1)) {
                        val newColumnPosition = column + shift
                        if (newColumnPosition >= 0 && newColumnPosition < line.length) {
                            newColumnPositions.add(newColumnPosition)
                        }
                    }
                    totalSplits ++
                } else {
                    newColumnPositions.add(column)
                }
            }

            columnPositions = newColumnPositions.toMutableList()
        }

        return totalSplits
    }

    fun part2(filename: String): Long {
        val input = readInput(filename).toMutableList()
        var columnPositions: MutableMap<Int, Long> = mutableMapOf()

        columnPositions[input[0].indexOf("S")] = 1
        input.removeFirst()

        for (line in input) {
            val newColumnPositions: MutableMap<Int, Long> = mutableMapOf()
            for((column, count) in columnPositions) {
                if (line[column] == '^') {
                    for (shift in listOf(-1, 1)) {
                        val newColumnPosition = column + shift
                        if (newColumnPosition >= 0 && newColumnPosition < line.length) {
                            val current = newColumnPositions[newColumnPosition] ?: 0
                            newColumnPositions[newColumnPosition] = current + count
                        }
                    }
                } else {
                    val current = newColumnPositions[column] ?: 0
                    newColumnPositions[column] = current + count
                }
            }

            columnPositions = newColumnPositions
        }

        return columnPositions.values.sum()
    }

    // Public tests
//    require(part1("$folderName/test") == 21)
//    require(part2("$folderName/test") == 40)

    val result = part2("$folderName/input")
    println(result)
}