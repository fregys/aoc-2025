import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/2
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Long {
        val input = readInput(filename)
        val ranges = input[0].split(",")
        var invalidIds : Long = 0

        for (range in ranges) {
            val splitRange = range.split("-")
            val curValue = splitRange[0].toLong()
            val endValue = splitRange[1].toLong()

            for (i in curValue..endValue) {
                val iString = i.toString()
                val curValueLength = iString.length
                if (curValueLength % 2 == 1) {
                    continue
                }

                if (iString.substring(0, iString.length / 2) == iString.substring(iString.length / 2, iString.length)) {
                    invalidIds += i
                }
            }
        }

        return invalidIds
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)
        val ranges = input[0].split(",")
        var invalidIds : Long = 0

        for (range in ranges) {
            val splitRange = range.split("-")
            val curValue = splitRange[0].toLong()
            val endValue = splitRange[1].toLong()

            for (number in curValue..endValue) {
                val numberString = number.toString()

                for (lastDigit in 1..numberString.length) {
                    val sequence = numberString.substring(0, lastDigit)

                    if (numberString.length % sequence.length != 0) continue
                    if (sequence.length > numberString.substring(0, numberString.length / 2).length) continue

                    var invalid = true
                    val chunks = numberString.chunked(sequence.length).toMutableList()
                    chunks.removeFirst()
                    for (chunk in chunks) {
                        if (chunk != sequence) {
                            invalid = false
                            break
                        }
                    }

                    if (invalid) {
                        invalidIds += numberString.toLong()
                        break
                    }
                }
            }
        }

        return invalidIds
    }

    // Public tests
    require(part1("$folderName/test") == 1227775554.toLong())
    require(part2("$folderName/test") == 4174379265.toLong())

    val result = part2("$folderName/input")
    print(result)
}