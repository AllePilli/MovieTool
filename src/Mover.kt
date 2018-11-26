import Functions.Companion.prompt
import java.io.File
import java.nio.file.Paths

class Mover{
    companion object {
        fun start(sourceUrl: String, destUrl: String){
            val src = Paths.get(sourceUrl)

            if (src.isDirectory()){
                val dir = File(sourceUrl)
                dir.walkTopDown().forEach { if(it.isFile) move("$sourceUrl\\${it.name}", destUrl) }
            }else{
                move(sourceUrl, destUrl)
            }
        }

        private fun move(sourceUrl: String, destUrl: String){
            val src = Paths.get(sourceUrl)
            val name = File(sourceUrl).name
            val dest = Paths.get("$destUrl\\$name")

            if (dest.exists()){
                val answer = prompt("$name already exists in $destUrl! Replace (y/n)?")
                if (answer.toLowerCase() == "y"){
                    src.move(dest, true)
                    println("Moving $name complete")
                }else println("Canceled $name...")
            }else{
                src.move(dest)
                println("Moving $name complete")
            }
        }
    }
}