import java.lang.Long.max
import java.lang.Long.min
import java.lang.invoke.MethodHandles
import kotlin.math.abs


/*
https://adventofcode.com/2025/day/9
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    data class Point(val x: Long, val y: Long)

    fun part1(filename: String): Long {
        val input = readInput(filename)

        val redTilesPosition: MutableSet<Point> = mutableSetOf()
        var maxRectangleSize: Long = 0
        for (line in input) {
            val lineSplit = line.split(",")
            val newRedTilePosition = Point(lineSplit[0].toLong(), lineSplit[1].toLong())
            for(redTilePosition in redTilesPosition) {
                val rectangleSize = (abs(newRedTilePosition.x - redTilePosition.x)+ 1) * (abs(newRedTilePosition.y - redTilePosition.y)+ 1)
                if (rectangleSize > maxRectangleSize) maxRectangleSize = rectangleSize
            }
            redTilesPosition.add(newRedTilePosition)
        }


        return maxRectangleSize
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)

        val redTilesPosition: MutableSet<Point> = mutableSetOf()
        val pointsOnXAxis: MutableSet<Point> = mutableSetOf()
        val pointsOnYAxis: MutableSet<Point> = mutableSetOf()
        var maxRectangleSize: Long = 0
        var previousRedTile: Point? = null
        for (line in input) {
            val lineSplit = line.split(",")
            val newRedTilePosition = Point(lineSplit[0].toLong(), lineSplit[1].toLong())

            if (previousRedTile != null){
                for (greenTileX in min(newRedTilePosition.x,previousRedTile.x)..max(newRedTilePosition.x,previousRedTile.x)) {
//                    val current = pointsToLines['y'] ?: mutableSetOf()
//                    pointsToLines['y'] = current
//                    current.add(Point(greenTileX, previousRedTile.y))
                }
                for (greenTileY in min(newRedTilePosition.y,previousRedTile.y)..max(newRedTilePosition.y,previousRedTile.y)) {
//                    val current = pointsToLines['x'] ?: mutableSetOf()
//                    pointsToLines['x'] = current
//                    current.add(Point(previousRedTile.x, greenTileY))
                }
            }
            previousRedTile = newRedTilePosition
            redTilesPosition.add(newRedTilePosition)
            // TODO last one to the first one
        }

        val greenRedTilesPosition: MutableSet<Point> = mutableSetOf()

//        for (tile in pointsToLines['y']) {
//
//        }

        return maxRectangleSize
    }

    // Public tests
//    require(part1("$folderName/test") == 50)
//    require(part2("$folderName/test") == 0)

    val result = part2("$folderName/input")
    println(result)
}