import Functions.Companion.prompt
import SRTTools.TimeStamp
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

fun main(args: Array<String>){
    val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
    jfc.dialogTitle = "Choose directory: "
    jfc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    jfc.currentDirectory = File("${System.getProperty("user.home")}\\Videos\\Movies")

    when (prompt("Function?")){
        "prep" ->
        {
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                val path = jfc.selectedFile.absolutePath
                Renamer.start(path, prompt("Episode prefix?"), prompt("New name?"))
                SRTFixer.removeAds(path, prompt("Recursive prep? [y/n]").toLowerCase() == "y")
            }else throw IllegalArgumentException("Must choose a directory for prep function")
        }
        "ren" ->
        {
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                Renamer.start(jfc.selectedFile.absolutePath, prompt("Episode prefix?"), prompt("New name?"))
            }else throw IllegalArgumentException("Must choose a directory for ren function")
        }
        "mov" ->
        {
            jfc.dialogTitle = "Choose source directory: "

            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                val src = jfc.selectedFile.absolutePath
                jfc.dialogTitle = "Choose destination directory: "
                jfc.currentDirectory = File("Y:\\Film")

                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    val dest = jfc.selectedFile.absolutePath
                    Mover.start(src, dest)
                }else throw IllegalArgumentException("Destination must be a directory")
            }else throw IllegalArgumentException("Source must be a directory")
        }
        "rad" ->
        {
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                SRTFixer.removeAds(jfc.selectedFile.absolutePath, prompt("Recursive? [y/n]").toLowerCase() == "y")
            }
        }
        "sh" ->
        {
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                SRTFixer.shift(jfc.selectedFile.absolutePath,
                    TimeStamp(TimeStamp.toFormat(prompt("Timestamp [00:00:00,000]?"))),
                    prompt("Forward? [y/n]").toLowerCase() == "y",
                    prompt("Recursice? [y/n]").toLowerCase() == "y")
            }
        }
        "help" ->
        {
            println("prep \t Rename file and remove ads: prep [-r] path epPrefix newName")
            println("ren \t Rename files in directory: ren path epPrefix newName")
            println("mov \t Move the file from sourceUrl to destUrl and create appropriate folder: mov src dest")
            println("rad \t Remove ads from srt file: rad [-r] path")
            println("sh \t shift subtitle of srt file: sh [-r] path [-]00:00:00,000")
            println("help \t list all commands: help")
        }
        else -> throw IllegalArgumentException("Not a function")
    }
}