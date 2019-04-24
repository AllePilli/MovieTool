import Functions.Companion.prompt
import SRTTools.TimeStamp
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

fun main() {
    val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
    jfc.dialogTitle = "Choose directory: "
    jfc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    jfc.currentDirectory = File("${System.getProperty("user.home")}\\Videos\\Movies")

    var func: String? = null

    while (!{ func = prompt("MovieTool$> "); func }().equals("q", ignoreCase = true)){
        when (func){
            "prep" ->
            {
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    val path = jfc.selectedFile.absolutePath
                    Renamer.start(path)
                    SRTFixer.removeAds(path, prompt("Recursive prep? [y/n] ").toLowerCase() == "y")
                }else println("Must choose a directory for prep function")
            }
            "ren" ->
            {
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    Renamer.start(jfc.selectedFile.absolutePath)
                }else println("Must choose a directory for ren function")
            }
            "mov" ->
            {
                jfc.dialogTitle = "Choose Series: "
                jfc.currentDirectory = File("Y:\\Film")

                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    Mover.start("${System.getProperty("user.home")}\\Videos\\Movies\\ToMove", jfc.selectedFile.absolutePath)
                }else println("Destination must be a directory: ${jfc.selectedFile.pureName()}")
            }
            "rad" ->
            {
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    SRTFixer.removeAds(jfc.selectedFile.absolutePath, prompt("Recursive? [y/n] ").toLowerCase() == "y")
                }
            }
            "sh" ->
            {
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    SRTFixer.shift(jfc.selectedFile.absolutePath,
                        TimeStamp(TimeStamp.toFormat(prompt("Timestamp [00:00:00,000]? => "))),
                        prompt("Forward? [y/n] ").toLowerCase() == "y",
                        prompt("Recursive? [y/n] ").toLowerCase() == "y")
                }
            }
            "help" ->
            {
                println("\tprep \t Rename file and remove ads: prep [-r] path newName")
                println("\tren \t Rename files in directory: ren path newName")
                println("\tmov \t Move the file from ToMove folder to destUrl and create appropriate folders: mov src dest")
                println("\trad \t Remove ads from srt file: rad [-r] path")
                println("\tsh \t shift subtitle of srt file: sh [-r] path [-]00:00:00,000")
                println("\thelp \t list all commands: help")
                println("\tq \tquit")
            }
            else -> println("$func is not a function")
        }
    }
}