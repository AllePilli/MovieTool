import SRTTools.SRTException
import SRTTools.Subtitle
import SRTTools.SubtitleFile
import java.io.File
import java.nio.file.Path

class SRTFixer{
    companion object {
        private fun removeAdsFromFile(file: File){
            if (file.extension != "srt") throw SRTException("File is not from srt format")

            val subFile = SubtitleFile.SubtitleFileFrom(file)
            val toRemove: MutableList<Subtitle> = mutableListOf()

            // ads always have a website or contain "=="
            subFile.subtitles.forEach { subtitle ->
                var reclame = false
                subtitle.lines.forEach { line -> if (line.contains("www.") || line.contains("==") || line.contains(".com") || line.contains(".COM")) reclame = true }
                if (reclame) toRemove.add(subtitle)
            }

            toRemove.forEach { sub ->
                subFile.subtitles.remove(sub)
                println("Removed ads from $sub")
            }

            subFile.save(file)
        }

        private fun removeAdsFromDirectory(file: File, recursive: Boolean){
            var firstTime = true

            if (recursive){
                file.walkTopDown().forEach {
                    if (firstTime) firstTime = false
                    else{
                        if (it.isFile && it.extension == "srt") removeAdsFromFile(it)
                        else removeAdsFromDirectory(it, recursive)
                    }
                }
            }else file.walkTopDown().forEach {
                if (firstTime) firstTime = false
                else{
                    if (it.isFile && it.extension == "srt") removeAdsFromFile(it)
                }
            }
        }

        fun removeAds(path: String, recursive: Boolean = false){
            val file = File(path)

            if (file.isFile) removeAdsFromFile(file)
            else removeAdsFromDirectory(file, recursive)
        }
    }
}