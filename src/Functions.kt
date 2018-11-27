import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Functions{
    companion object {
        /**
         * Prompt user with a message, and wait for an answer
         */
        fun prompt(msg: String): String{
            print("$msg =>")
            return readLine() ?: ""
        }

        /**
         * creates a directory from a String, and returns it
         */
        fun createDirectory(name: String, directoryPath: String) = createDirectory(name, File(directoryPath))

        /**
         * Creates a directory from a File, and returns it
         */
        fun createDirectory(name: String, directory: File): File {
            var dir = directory
            dir.mkdir()
            dir = File(dir, name)
            Files.createDirectory(dir.toPath())
            return dir
        }

        /**
         * Moves a File from sourceUrl to destUrl
         */
        fun move(sourceUrl: String, destUrl: String){
            val src = Paths.get(sourceUrl)
            val name = File(sourceUrl).name
            val dest = Paths.get("$destUrl\\$name")

            println("Moving $name")

            if (dest.exists()){
                val answer = prompt("$name already exists in $destUrl! Replace (y/n)?")
                if (answer.toLowerCase() == "y"){
                    src.move(dest, true)
                    println("Moved $name")
                }else println("Canceled $name...")
            }else{
                src.move(dest)
                println("Moved $name")
            }
        }
    }
}