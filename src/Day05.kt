import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/5
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
        val finalRanges: MutableSet<Pair<Long, Long>> = mutableSetOf()

        var freshIds: Long = 0
        for (line in input) {
            if (line == "") {
                break
            }

            val newLine = line.split("-")
            var curMinValue = newLine[0].toLong()
            var curMaxValue = newLine[1].toLong()

            if (finalRanges.isEmpty()) {
                finalRanges.add(Pair(curMinValue, curMaxValue))
                continue
            }

            var addRange = false
            for (finalRange in finalRanges.toList()){
                if ((curMinValue >= finalRange.first && curMinValue <= finalRange.second)
                    || (curMaxValue >= finalRange.first && curMaxValue <= finalRange.second)) {

                    if (!(curMinValue >= finalRange.first && curMinValue <= finalRange.second)) {
                        // Only upper bound is in
                        curMaxValue = finalRange.first - 1
                    } else if (!(curMaxValue >= finalRange.first && curMaxValue <= finalRange.second)) {
                        // Only lower bound is in
                        curMinValue = finalRange.second + 1
                    } else {
                        // Both bounds are in
                        addRange = true
                        break
                    }
                } else if (curMinValue < finalRange.first && curMaxValue > finalRange.second){
                    // On both sides
                    finalRanges.remove(finalRange)
                }
                // On one of the sides of the range
            }

            if (!addRange) finalRanges.add(Pair(curMinValue, curMaxValue))
        }

        for (range in finalRanges) {
            freshIds += range.second - range.first + 1
        }

        return freshIds
    }

    // Public tests
//    require(part1("$folderName/test") == 3)
//    require(part2("$folderName/test") == 14.toLong())

    val result = part2("$folderName/input")
    println(result)
}