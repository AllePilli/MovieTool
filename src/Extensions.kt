import java.io.File
import java.lang.IllegalArgumentException

/**
 * return the name of a file without the extension
 */
fun File.pureName() = if (this.isFile) this.name.split(this.extension)[0] else this.absolutePath