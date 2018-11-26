package SRTTools

data class Subtitle(var startTime: TimeStamp, var endTime: TimeStamp, var lines: MutableList<String> = mutableListOf()){
    companion object {
        fun formatLine(line: String) = line.replace("\r\n", "\n").replace("\n\n", "\n \n")
    }

    fun compile(index: Int): String{
        var subtitle = "$index\n${startTime.compile()} --> ${endTime.compile()}\n"
        lines.forEach { subtitle += "$it\n" }
        subtitle += "\n"

        return subtitle
    }
}