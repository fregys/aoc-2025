import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput(name: String) = Path("input/$name.txt").readText().trim().lines()