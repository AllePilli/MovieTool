import Functions.Companion.prompt
import SRTTools.TimeStamp
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

fun main(args: Array<String>){
    val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
    jfc.dialogTitle = "Choose directory: "
    jfc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

    when (prompt("Function?")){
        "prep" -> { // Rename file and remove ads: prep [-r] path epPrefix newName
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                val path = jfc.selectedFile.absolutePath
                Renamer.start(path, prompt("Episode prefix = ?"), prompt("New name = ?"))
                SRTFixer.removeAds(path, prompt("Recursive prep? [y/n]").toLowerCase() == "y")
            }else throw IllegalArgumentException("Must choose a directory for prep function")
        }
        "ren" -> { // Rename files in directory: ren path epPrefix newName
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                Renamer.start(jfc.selectedFile.absolutePath, prompt("Episode prefix = ?"), prompt("New name = ?"))
            }else throw IllegalArgumentException("Must choose a directory for ren function")
        }
        "mov" -> { // Move the file from sourceUrl to destUrl: mov src dest
            jfc.dialogTitle = "Choose source directory: "

            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                val src = jfc.selectedFile.absolutePath
                jfc.dialogTitle = "Choose destination directory: "

                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    val dest = jfc.selectedFile.absolutePath
                    Mover.start(src, dest)
                }else throw IllegalArgumentException("Destination must be a directory")
            }else throw IllegalArgumentException("Source must be a directory")
        }
        "rad" -> { // Remove ads from srt file: rad [-r] path
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                SRTFixer.removeAds(jfc.selectedFile.absolutePath, prompt("Recursive? [y/n]").toLowerCase() == "y")
            }
        }
        "sh" -> { // sh [-r] path [-]00:00:00,000
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                SRTFixer.shift(jfc.selectedFile.absolutePath,
                    TimeStamp(TimeStamp.toFormat(prompt("Timestamp = ?"))),
                    prompt("Forward? [y/n]").toLowerCase() == "y",
                    prompt("Recursice? [y/n]").toLowerCase() == "y")
            }
        }
        else -> throw IllegalArgumentException("Not a function")
    }
}