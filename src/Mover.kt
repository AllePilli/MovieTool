import Functions.Companion.createDirectory
import Functions.Companion.move
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
    }
}