import Functions.Companion.prompt
import java.io.File

class Renamer{
    companion object {
        fun start(path: String, name: String){
            val regex = "S\\d\\dE\\d\\d".toRegex()
            val dir = File(path)
            dir.walkTopDown().forEach {
                if (it.isFile) {
                    val match = regex.find(it.pureName())
                    val epPrefix: String

                    if (match != null){
                        val value = match.value
                        var s = value.substring(1, 3)
                        val e = value.substring(3, value.length).substring(1, 3)
                        if (s[0] == '0') s = s.substring(1, s.length)

                        epPrefix = "${s}x$e"
                    }else epPrefix = prompt("Episode prefix?")

                    if(it.renameTo(File("${path}\\${epPrefix}-${name}.${it.extension}"))) println("Renamed ${it.name}")
                }
            }
        }
    }
}