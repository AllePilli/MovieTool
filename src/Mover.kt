import Functions.Companion.prompt
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Mover{
    companion object {
        fun start(sourceUrl: String, destUrl: String){
            val src = Paths.get(sourceUrl)
            var episode: String
            var destFile = File("")

            if (src.isDirectory()){
                val dir = File(sourceUrl)
                var firstTime = true

                dir.walkTopDown().forEach {
                    if(it.isFile) {
                        if (firstTime){
                            firstTime = false
                            episode = it.pureName().split("-")[0].split("x")[1]
                            destFile = createDirectory(episode, destUrl)
                        }

                        move("$sourceUrl\\${it.name}", destFile.absolutePath)
                    }
                }
            }else{
                episode = File(sourceUrl).pureName().split("-")[0].split("x")[1]
                destFile = createDirectory(episode, destUrl)

                move(sourceUrl, destFile.absolutePath)
            }
        }

        fun createDirectory(name: String, directoryPath: String) = createDirectory(name, File(directoryPath))

        fun createDirectory(name: String, directory: File): File{
            var dir = directory
            dir.mkdir()
            dir = File(dir, name)
            Files.createDirectory(dir.toPath())
            return dir
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