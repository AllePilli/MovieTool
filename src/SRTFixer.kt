import SRTTools.SRTException
import SRTTools.Subtitle
import SRTTools.SubtitleFile
import java.io.File
import java.nio.file.Path

class SRTFixer{
    companion object {
        fun removeAds(file: File){
            if (file.extension != "srt") throw SRTException("File is not from srt format")

            val subFile = SubtitleFile.SubtitleFileFrom(file)
            val toRemove: MutableList<Subtitle> = mutableListOf()

            // ads always have a website
            subFile.subtitles.forEach { subtitle ->
                var reclame = false
                subtitle.lines.forEach { line -> if (line.contains("www.") || line.contains("==")) reclame = true }
                if (reclame) toRemove.add(subtitle)//subFile.subtitles.remove(subtitle)
            }

            toRemove.forEach { sub ->
                subFile.subtitles.remove(sub)
            }

            subFile.save(file)
        }

        fun removeAds(path: String){
            removeAds(File(path))
        }
    }
}