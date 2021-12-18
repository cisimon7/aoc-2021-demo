import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.time.ExperimentalTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(path: String) = File("src", path).readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


@OptIn(ExperimentalTime::class)
fun <T> runWithTime(block: () -> T) {
    val (result1, time1) = kotlin.time.measureTimedValue { block() }
    println(mapOf("Time (ms)" to time1.toString()) + mapOf("Result" to result1))
}
