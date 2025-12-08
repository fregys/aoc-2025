import java.lang.invoke.MethodHandles
import java.util.SortedMap
import kotlin.math.sqrt
import kotlin.math.pow


/*
https://adventofcode.com/2025/day/8
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    data class Point(val x: Int, val y: Int, val z: Int)

    fun getDistance(pointA: Point, pointB: Point): Double {
        return sqrt((pointA.x - pointB.x).toDouble().pow(2)
            + (pointA.y - pointB.y).toDouble().pow(2)
            + (pointA.z - pointB.z).toDouble().pow(2))
    }

    fun getDistancesMap(input: List<String>): Pair<SortedMap<Double, Pair<Int, Int>>, MutableMap<Point, Int>> {
        val distances: SortedMap<Double, Pair<Int, Int>> = sortedMapOf()
        val boxesLocation: MutableMap<Point, Int> = mutableMapOf()

        for ((newIndex, line) in input.withIndex()) {
            val coordinates = line.split(",")
            val newPoint = Point(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt())

            for ((boxLocation, oldIndex) in boxesLocation) {
                val newDistance = getDistance(newPoint, boxLocation)
                distances[newDistance] = Pair(newIndex, oldIndex)
            }

            boxesLocation[newPoint] = newIndex
        }

        return Pair(distances, boxesLocation)
    }

    fun findInCurrentCircuits(boxes: Pair<Int, Int>, circuits: MutableList<MutableList<Int>>): MutableList<Pair<Int, Int>> {
        val foundBoxesInCircuits: MutableList<Pair<Int, Int>> = mutableListOf()

        val boxesList = boxes.toList()
        for (box in boxesList) {
            val b = boxesList.toMutableList()
            for ((index, circuit) in circuits.withIndex()) {
                if (circuit.contains(box)){
                    b.remove(box)
                    foundBoxesInCircuits.add(Pair(b[0], index))
                    break
                }
            }
        }

        return foundBoxesInCircuits
    }

    fun updateCircuits(boxes: Pair<Int, Int>, circuits: MutableList<MutableList<Int>>): MutableList<MutableList<Int>>{
        val foundBoxesInCircuits: MutableList<Pair<Int, Int>> = findInCurrentCircuits(boxes, circuits)

        if (foundBoxesInCircuits.size == 2 && foundBoxesInCircuits[0].second != foundBoxesInCircuits[1].second) {
            circuits[foundBoxesInCircuits[0].second].addAll(circuits[foundBoxesInCircuits[1].second])
            circuits.removeAt(foundBoxesInCircuits[1].second)
        } else if (foundBoxesInCircuits.size == 1){
            circuits[foundBoxesInCircuits[0].second].add(foundBoxesInCircuits[0].first)
        } else if (foundBoxesInCircuits.isEmpty()){
            circuits.add(mutableListOf(boxes.first, boxes.second))
        }

        return circuits
    }

    fun part1(filename: String): Int {
        val input = readInput(filename)

        val distanceInput = getDistancesMap(input)
        val distances = distanceInput.first
        val boxesLocation = distanceInput.second

        val CONNECT_NUM = 10
        val circuits: MutableList<MutableList<Int>> = mutableListOf()

        var connectionCnt = 0
        for ((distance, boxes) in distances) {
            if (connectionCnt == CONNECT_NUM) break

            updateCircuits(boxes, circuits)

            connectionCnt++
        }

        var largestCircuitMult = 1
        circuits.sortBy { it.size }
        circuits.reverse()
        for ((index, circuit) in circuits.withIndex()) {
            if (index == 3) break
            largestCircuitMult *= circuit.size
        }

        return largestCircuitMult
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)

        val distanceInput = getDistancesMap(input)
        val distances = distanceInput.first
        val boxesLocation = distanceInput.second

        val circuits: MutableList<MutableList<Int>> = mutableListOf()
        var finalJunctionPointMult: Long = 0
        for ((distance, boxes) in distances) {
            updateCircuits(boxes, circuits)

            if (circuits.size == 1 && circuits[0].size == input.size) {
                finalJunctionPointMult = input[boxes.first].split(",")[0].toLong() * input[boxes.second].split(",")[0].toLong()
                break
            }
        }

        return finalJunctionPointMult
    }

    // Public tests
//    require(part1("$folderName/test") == 40)
//    require(part2("$folderName/test") == 25272)

    val result = part2("$folderName/input")
    println(result)
}