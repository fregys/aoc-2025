import java.lang.invoke.MethodHandles



class D11Rec(val allPaths: MutableMap<String, MutableSet<String>>) {
    var totalPaths: Long = 0

    fun run(node: String, visitedNodes: MutableSet<String>) {
        if (node == "out") {
            totalPaths++
            return
        }
        if (visitedNodes.contains(node)) return
        val newVisitedNodes = visitedNodes.toMutableSet()
        newVisitedNodes.add(node)

        for (connection in allPaths[node]!!) {
            run( connection, newVisitedNodes)
        }
    }
}

class D11Rec2(val allPaths: MutableMap<String, MutableSet<String>>) {
    val mustVisit: MutableList<String> = mutableListOf("dac", "fft")
    val mem: MutableMap<MutableList<Boolean>, MutableMap<String, Long>> = mutableMapOf()

    fun run(node: String, level: MutableList<Boolean>): Long {
        if (mem[level]?.get(node) != null) return mem[level]?.get(node) ?: throw IllegalStateException()

        mem.getOrPut(level) { mutableMapOf() }[node] = 0

        if (node == "out") {
            if (level.all { it }) {
                mem[level]?.set(node, 1)
            }
            return mem[level]?.get(node) ?: throw IllegalStateException()
        }

        val newLevel = level.toMutableList()
        val index = mustVisit.indexOf(node)
        if (index != -1) {
            newLevel[index] = true
        }

        val pathsFromConnections: Long = allPaths[node].orEmpty().sumOf { connection ->
            run(connection, newLevel)
        }
        mem.getOrPut(level) { mutableMapOf() }[node] = pathsFromConnections
        return pathsFromConnections
    }
}


/*
https://adventofcode.com/2025/day/11
 */
fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Long {
        val input = readInput(filename)
        val allPaths: MutableMap<String, MutableSet<String>> = mutableMapOf()

        for (line in input) {
            val splitLines = line.split(": ")
            for (connection in splitLines[1].split(" ")) {
                val current = allPaths[splitLines[0]] ?: mutableSetOf()
                current.add(connection)
                allPaths[splitLines[0]] = current
            }
        }

        val recCls = D11Rec(allPaths)
        recCls.run("you",mutableSetOf())

        return recCls.totalPaths
    }

    fun part2(filename: String): Long {
        val input = readInput(filename)
        val allPaths: MutableMap<String, MutableSet<String>> = mutableMapOf()

        for (line in input) {
            val splitLines = line.split(": ")
            for (connection in splitLines[1].split(" ")) {
                val current = allPaths[splitLines[0]] ?: mutableSetOf()
                current.add(connection)
                allPaths[splitLines[0]] = current
            }
        }

        val recCls = D11Rec2(allPaths)
        recCls.run("svr", mutableListOf(false, false))

        return recCls.mem[mutableListOf(false, false)]!!["svr"]!!
    }

    // Public tests
    require(part1("$folderName/test") == 5.toLong())
    require(part2("$folderName/test2") == 2.toLong())

    val result = part2("$folderName/input")
    println(result)
}