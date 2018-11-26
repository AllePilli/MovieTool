package SRTTools

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.*

data class SubtitleFile(val subtitles: MutableList<Subtitle> = mutableListOf()){
    companion object {
        fun SubtitleFileFrom(file: File): SubtitleFile{
            val subtitles: MutableList<Subtitle> = mutableListOf()
            val input = FileInputStream(file)
            val scanner = Scanner(input)

            while (scanner.hasNextLine()){
                scanner.nextLine()

                val timestamps = scanner.nextLine().split(" --> ")
                if (timestamps.size != 2) throw InvalidTimestampFormatException()

                val startTime = TimeStamp(TimeStamp.toFormat(timestamps[0]))
                val endTime = TimeStamp(TimeStamp.toFormat(timestamps[1]))

                val subtitle = Subtitle(startTime, endTime)
                var line = scanner.nextLine()

                while (line != ""){
                    subtitle.lines.add(line)
                    line = scanner.nextLine()
                }

                subtitles.add(subtitle)
            }

            return SubtitleFile(subtitles)
        }
    }

    fun compile(): String{
        var str = ""
        var index = 1

        subtitles.forEach { subtitle ->
            str += subtitle.compile(index)
            index++
        }

        return str
    }

    fun save(file: File){
        val out = FileOutputStream(file)
        out.write(compile().toByteArray(Charset.forName("UTF-8")))
        out.close()
    }
}