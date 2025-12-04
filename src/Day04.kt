import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/4
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    val combinations: List<Pair<Int, Int>> = listOf(
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, 1),
        Pair(1, 1),
        Pair(1, 0),
        Pair(1, -1),
        Pair(0, -1),
        Pair(-1, -1),
    )

    fun isAdjacent(diagram: MutableList<MutableList<String>>, rowIndex: Int, columnIndex: Int): Boolean {
        if (
            rowIndex < 0 || columnIndex < 0 ||
            rowIndex >= diagram[0].size || columnIndex >= diagram.size
            ) return false

        if (diagram[rowIndex][columnIndex] == "@") return true
        return false
    }

    fun part1(filename: String): Int {
        val input = readInput(filename)
        val diagram: MutableList<MutableList<String>> = mutableListOf()
        var accessibleRolls = 0

        for (line in input) {
            diagram.add(line.chunked(1).toMutableList())
        }
        // Maybe create graph instead of 2Dd array and check neighbours
        for ((rowIndex,row) in diagram.withIndex()) {
            for ((columnIndex, symbol) in row.withIndex()) {
                if (symbol != "@") continue

                var adjacentCnt = 0
                for (combination in combinations) {
                    if(isAdjacent(diagram, rowIndex + combination.first, columnIndex + combination.second)) adjacentCnt++
                }

                if(adjacentCnt < 4) accessibleRolls++
            }
        }

        return accessibleRolls
    }

    fun part2(filename: String): Int {
        val input = readInput(filename)
        val diagram: MutableList<MutableList<String>> = mutableListOf()
        var removedRolls = 0

        for (line in input) {
            diagram.add(line.chunked(1).toMutableList())
        }
        while (true) {
            var rollsIndexToBeRemoved: MutableList<Pair<Int, Int>> = mutableListOf()
            for ((rowIndex,row) in diagram.withIndex()) {
                for ((columnIndex, symbol) in row.withIndex()) {
                    if (symbol != "@") continue

                    var adjacentCnt = 0
                    for (combination in combinations) {
                        if(isAdjacent(diagram, rowIndex + combination.first, columnIndex + combination.second)) adjacentCnt++
                    }

                    if(adjacentCnt < 4){
                        rollsIndexToBeRemoved.add(Pair(rowIndex, columnIndex))
                        removedRolls++
                    }
                }
            }

            for (rollIndexToBeRemoved in rollsIndexToBeRemoved) {
                diagram[rollIndexToBeRemoved.first][rollIndexToBeRemoved.second] = "x"
            }

            if (rollsIndexToBeRemoved.isEmpty()) break
        }

        return removedRolls
    }

    // Public tests
    require(part1("$folderName/test") == 13)
    require(part2("$folderName/test") == 43)

    val result = part2("$folderName/input")
    println(result)
}