import java.lang.Math.pow
import java.lang.invoke.MethodHandles

fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Int {
        val input = readInput(filename)
        val ranges = input[0].split(",")
        var invalidIds = 0

        // First Idea of faster computing, but it's taking too much time :)
        for (range in ranges) {
            val splitRange = range.split("-")
            var curValue = splitRange[0].toInt()
            val endValue = splitRange[1].toInt()

            while (true) {
                val curValueLength = curValue.toString().length

                if (curValue > endValue) break
                if (curValueLength % 2 == 1) {
                    curValue = pow(10.toDouble(), curValueLength.toDouble()).toInt()
                    continue
                }

                val sequence = curValue.toString()

                var first = sequence.substring(0, curValueLength / 2).toInt()

                if ((first >= sequence.substring(curValueLength / 2, sequence.length).toInt()) &&
                    ((sequence.substring(0, curValueLength / 2) + sequence.substring(0, curValueLength / 2)).toInt() <= endValue)) {
                    invalidIds += (sequence.substring(0, curValueLength / 2) + sequence.substring(0, curValueLength / 2)).toInt()
                }

                curValue = (first + 1) * pow(10.toDouble(), sequence.substring(0, curValueLength / 2).length.toDouble()).toInt()
            }

            // For sure can loop through every single number in the range

            print(invalidIds)

        }

        return 0
    }

    fun part2(filename: String): Int {
        return 0
    }

    // Public test
//     assert(part1("Day02/test") == 1227775554)

    val result = part1("$folderName/test")
    print(result)
}