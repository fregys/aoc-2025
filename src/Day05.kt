import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/4
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Int {
        val input = readInput(filename)
        val ranges: MutableList<Pair<Long, Long>> = mutableListOf()
        var freshIds = 0

        for (line in input) {
            if (line.contains("-")) {
                val range = line.split("-")

                ranges.add(Pair(range[0].toLong(), range[1].toLong()))
            } else if (line == "") {
                continue
            } else {
                for (range in ranges) {
                    if (line.toLong() >= range.first && line.toLong() <= range.second){
                        freshIds++
                        break
                    }
                }
            }
        }

        return freshIds
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)
        val freshIdsSet: MutableSet<Long> = mutableSetOf()
        var freshIds: Long = 0

        for (line in input) {
            if (line == "") {
                break
            }

            val lineSplit = line.split("-")
            for (id in lineSplit[0].toLong()..lineSplit[1].toLong()) {
                if (!freshIdsSet.contains(id)) {
                    freshIds ++
                    freshIdsSet.add(id)
                }
            }
        }

        return freshIds
    }

    // Public tests
//    require(part1("$folderName/test") == 3)
//    require(part2("$folderName/test") == 14.toLong())

    val result = part2("$folderName/input")
    println(result)
}