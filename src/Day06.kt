import java.lang.invoke.MethodHandles


/*
https://adventofcode.com/2025/day/6
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun calculateTotal(batchOperations: MutableList<String>, batchNumbers: MutableList<MutableList<Long>>): Long {
        var totalNumber: Long = 0
        for ((index, operation) in batchOperations.withIndex()) {
            val batch = batchNumbers[index]
            var totalColumn: Long = batch[0]
            batch.removeFirst()

            for (number in batch) {
                if (operation == "*") {
                    totalColumn *= number
                } else {
                    totalColumn += number
                }
            }

            totalNumber += totalColumn
        }

        return totalNumber
    }

    fun part1(filename: String): Long {
        val input = readInput(filename).toMutableList()
        val batchNumbers: MutableList<MutableList<Long>> = mutableListOf()
        val batchOperations: MutableList<String> = mutableListOf()

        var inputOperations = input[input.size - 1]
        input.removeLast()
        inputOperations = inputOperations.trim().replace("\\s+".toRegex(), " ")

        for (operation in inputOperations.split(" ")) {
            batchOperations.add(operation)
        }

        for (line in input) {
            val lineNormalized = line.trim().replace("\\s+".toRegex(), " ")
            for ((index, number) in lineNormalized.split(" ").withIndex()) {

                if (batchNumbers.size <= index) {
                    batchNumbers.add(mutableListOf(number.toLong()))
                } else {
                    batchNumbers[index].add(number.toLong())
                }
            }
        }

        return calculateTotal(batchOperations, batchNumbers)
    }

    fun part2(filename: String): Long {
        val input = readInput(filename).toMutableList()
        val columns: MutableList<MutableList<String>> = mutableListOf()
        val batchOperations: MutableList<String> = mutableListOf()

        var inputOperations = input[input.size - 1]
        input.removeLast()
        inputOperations = inputOperations.trim().replace("\\s+".toRegex(), " ")

        for (operation in inputOperations.split(" ")) {
            batchOperations.add(operation)
        }

        for (line in input) {
            for ((index, character) in line.withIndex()) {
                if (columns.size <= index) {
                    columns.add(mutableListOf(character.toString()))
                } else {
                    columns[index].add(character.toString())
                }
            }
        }


        val columnsMerged: MutableList<MutableList<Long>> = mutableListOf()
        var index = 0
        for (column in columns) {
            if (column.all { it == " " }) {
                index ++
                continue
            }

            var number = ""
            for (rowElement in column) {
                if (rowElement == " ") continue
                number += rowElement
            }

            if (columnsMerged.size <= index) {
                columnsMerged.add(mutableListOf(number.toLong()))
            } else {
                columnsMerged[index].add(number.toLong())
            }
        }

        return calculateTotal(batchOperations, columnsMerged)
    }

    // Public tests
    require(part1("$folderName/test") == 4277556.toLong())
    require(part2("$folderName/test") == 3263827.toLong())

    val result = part2("$folderName/input")
    println(result)
}