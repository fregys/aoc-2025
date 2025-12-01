import java.lang.invoke.MethodHandles

fun main() {
    val folderName = MethodHandles.lookup().lookupClass().simpleName.removeSuffix("Kt")

    fun part1(filename: String): Int {
        return 0
    }

    fun part2(filename: String): Int {
        return 0
    }

    // Public test
    // assert(part1("Day02/test") == 0)

    val result = part1("$folderName/input")
    print(result)
}