import java.io.File
import java.lang.IllegalArgumentException

/**
 * return the name of a file without the extension
 */
fun File.getName() = if (this.isFile) this.name.split(".")[0] else this.absolutePath