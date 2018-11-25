import java.io.File

class Renamer{
    companion object {
        fun start(path: String, epPrefix: String, name: String){
            val dir = File(path)
            dir.walkTopDown().forEach {
                if (it.isFile) it.renameTo(File("${path}\\${epPrefix}-${name}.${it.extension}"))
            }
        }
    }
}