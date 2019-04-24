import Functions.Companion.prompt
import java.io.File

class Renamer{
    companion object {//TODO: multiple files renamed at the same time
        private val SE_REGEX = "[sS]\\d{2}[eE]\\d{2}|[1-9][xX]\\d{2}".toRegex()
        private val CORRECT_REGEX = "^\\d{1,2}x\\d{2}-\\w+\\.\\w+$".toRegex()

        fun start(path: String){
            val dir = File(path)
            val done = hashMapOf<String, String>()

            dir.walkTopDown()
                .filter { !it.isDirectory }                         // Dont rename directories
                .filter { !CORRECT_REGEX.matches(it.pureName()) }   // Dont rename files with correct filenames
                .forEach { file ->
                val match = SE_REGEX.find(file.pureName())
                val epPrefix = if (match != null){
                    val matchedVal = match.value
                    var s = matchedVal.substring(1, 3)
                    val e = matchedVal.substring(3).substring(1, 3)
                    if (s[0] == '0') s = s.substring(1)

                    "${s}x$e"
                }else "${prompt("Season? => ")}x${prompt("Episode? => ")}"

                if (done[epPrefix] == null) done[epPrefix] = prompt("Name of $epPrefix? => ")

                val name = done[epPrefix]!!

                if (file.renameTo(File("$path\\$epPrefix-$name.${file.extension}"))) println("Renamed ${file.name}")
            }
        }
    }
}