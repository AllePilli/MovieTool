import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * return the name of a file without the extension
 */
fun File.pureName() = if (this.isFile) this.name.split(this.extension)[0] else this.absolutePath

/**
 * return true if the Path points to an existing file
 */
fun Path.exists() = Files.exists(this)

/**
 * return true if the path points to a file
 */
fun Path.isFile() = !Files.isDirectory(this)

/**
 * return true if the path points to a directory
 */
fun Path.isDirectory() = !isFile()

fun Path.move(dest: Path, overwrite: Boolean = false): Boolean{
    return if (isFile()){
        if (dest.exists()){
            if (overwrite){
                Files.move(this, dest, StandardCopyOption.REPLACE_EXISTING)
                true
            }else false
        }else{
            Files.move(this, dest)
            true
        }
    } else false
}