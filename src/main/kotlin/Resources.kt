
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Read an [InputStream] using a buffered reader and return it as a string.
 */
fun InputStream.readToString(charset: Charset = Charsets.UTF_8): String =
    bufferedReader(charset).use { bufferedReader -> bufferedReader.readText() }

/**
 * Write a string to an [OutputStream].
 */
fun OutputStream.writeString(string: String, charset: Charset = Charsets.UTF_8) {
    use { outputStream -> outputStream.write(string.toByteArray(charset)) }
}
