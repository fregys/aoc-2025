import java.lang.invoke.MethodHandles
import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPVariable


/*
https://adventofcode.com/2025/day/10
 */


class Rec(
    val mem: MutableMap<MutableList<Boolean>, Long> = mutableMapOf(),
    var indicatorDiagram: MutableList<Boolean> = mutableListOf(),
    val buttonsWirings: MutableSet<MutableSet<Int>> = mutableSetOf()
) {

    fun run(indicatorLights: MutableList<Boolean>, pressedCnt: Long) {
        if (mem.contains(indicatorLights) && pressedCnt >= mem[indicatorLights]!!) return
        if (indicatorLights == indicatorDiagram) {
            mem[indicatorLights] = pressedCnt
            return
        }

        mem[indicatorLights] = pressedCnt

        for (buttonsWiring in buttonsWirings) {
            val newIndicatorLights = indicatorLights.toMutableList()
            for (button in buttonsWiring) {
                newIndicatorLights[button] = newIndicatorLights[button].not()
            }
            run(newIndicatorLights, pressedCnt + 1)
        }
    }
}

class Rec2(
    val mem: MutableMap<MutableList<Int>, Int> = mutableMapOf(),
    var targetJoltages: MutableList<Int> = mutableListOf(),
    val buttonsWirings: MutableSet<MutableSet<Int>> = mutableSetOf()
) {

    fun run(currentJoltages: MutableList<Int>, pressedCnt: Int) {
        if (
            (mem.contains(currentJoltages) && pressedCnt >= mem[currentJoltages]!!) ||
            (mem.contains(targetJoltages) && pressedCnt >= mem[targetJoltages]!!)) return
        if (currentJoltages == targetJoltages) {
            mem[currentJoltages] = pressedCnt
            return
        }
        for (index in 0..<targetJoltages.size) {
            if (currentJoltages[index] > targetJoltages[index]) return
        }

        mem[currentJoltages] = pressedCnt

        for (buttonsWiring in buttonsWirings) {
            val newCurrentJoltages = currentJoltages.toMutableList()
            for (button in buttonsWiring) {
                newCurrentJoltages[button]++
            }
            run(newCurrentJoltages, pressedCnt + 1)
        }
    }
}

fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Long {
        val input = readInput(filename)

        var totalMin: Long = 0
        for (line in input) {
            val mem: MutableMap<MutableList<Boolean>, Long> = mutableMapOf()
            val indicatorDiagram: MutableList<Boolean> = mutableListOf()
            val buttonsWirings: MutableSet<MutableSet<Int>> = mutableSetOf()

            val splitLine = line.split(" ").toMutableList()

            for (light in splitLine[0].subSequence(1, splitLine[0].length-1)) {
                if (light == '.') indicatorDiagram.add(false)
                else indicatorDiagram.add(true)
            }

            for (buttonWiring in splitLine.subList(1, splitLine.size -1)) {
                val buttonsSchemaWiring: MutableSet<Int> = mutableSetOf()
                val buttonListCleaned = buttonWiring.replace("(", "").replace(")", "").split(",")
                for (button in buttonListCleaned) {
                    buttonsSchemaWiring.add(button.toInt())
                }
                buttonsWirings.add(buttonsSchemaWiring)
            }

            val recCls = Rec(mem, indicatorDiagram, buttonsWirings)
            recCls.run((MutableList(indicatorDiagram.size) {false}), 0)
            totalMin += recCls.mem[indicatorDiagram]!!
        }

        return totalMin
    }

    fun part2(filename: String): Double {
        val input = readInput(filename)

        Loader.loadNativeLibraries()

        var totalMin = 0.0
        for (line in input) {
            val targetJoltages: MutableList<Int> = mutableListOf()

            val solver = MPSolver.createSolver("CP-SAT")
            val objective = solver.objective()
            objective.setMinimization()

            for (targetJoltage in line.substringAfter("{").substringBefore('}').split(",")){
                targetJoltages.add(targetJoltage.toInt())
            }

            val splitLine = line.split(" ").toMutableList()
            val buttonsList = splitLine.subList(1, splitLine.size -1)
            val buttonsWirings: MutableList<MutableList<Int>> = MutableList(targetJoltages.size) {mutableListOf()}

            val solverVars: MutableList<MPVariable> = mutableListOf()
            for ((index, buttonWiring) in buttonsList.withIndex()) {
                val buttonListCleaned = buttonWiring.replace("(", "").replace(")", "").split(",")
                for (button in buttonListCleaned) {
                    buttonsWirings[button.toInt()].add(index)
                    val solverVar = solver.makeNumVar(0.0, Double.POSITIVE_INFINITY, index.toString())
                    solverVars.add(solverVar)
                    objective.setCoefficient(solverVar, 1.0)
                }
            }

            for ((index, targetJoltage) in targetJoltages.withIndex()) {
                val constraint = solver.makeConstraint(targetJoltage.toDouble(), targetJoltage.toDouble(), "c${index}")
                for (buttonWiring in buttonsWirings[index]) {
                    constraint.setCoefficient(solverVars[buttonWiring], 1.0)
                }
            }

            val resultStatus = solver.solve()
            if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
                throw IllegalStateException()
            }

            totalMin += objective.value()
        }

        return totalMin
    }

    // Public tests
//    require(part1("$folderName/test") == 7.toLong())
//    require(part2("$folderName/test") == 33.toLong())

    val result = part2("$folderName/input")
    println(result)
}