import SRTTools.SRTException
import SRTTools.Subtitle
import SRTTools.SubtitleFile
import SRTTools.TimeStamp
import java.io.File

class SRTFixer{
    companion object {
        private fun removeAdsFromFile(file: File){
            if (file.extension != "srt") throw SRTException("File is not from srt format")

            val subFile = SubtitleFile.SubtitleFileFrom(file)
            val toRemove: MutableList<Subtitle> = mutableListOf()

            subFile.subtitles.forEach { subtitle ->
                var reclame = false
                subtitle.lines.forEach { line -> if (line.contains("www.") || line.contains("==") || line.contains(".com") || line.contains(".COM") || line.contains(".org") || line.contains(".net")) reclame = true }
                if (reclame) toRemove.add(subtitle)
            }

            toRemove.forEach { sub ->
                subFile.subtitles.remove(sub)
                println("Removed ads from $sub")
            }

            subFile.save(file)
        }

        private fun inDirectory(file: File, recursive: Boolean, fnFile: (f: File) -> Unit){
            var firstTime = true

            if (recursive){
                file.walkTopDown().forEach{
                    if (firstTime) firstTime = false
                    else{
                        if (it.isFile && it.extension == "srt") fnFile(it)
                        else inDirectory(it, recursive, fnFile)
                    }
                }
            }else file.walkTopDown().forEach {
                if (firstTime) firstTime = false
                else{
                    if (it.isFile && it.extension == "srt") fnFile(it)
                }
            }
        }

        private fun inDirectory(file: File, recursive: Boolean, fnFile: (f: File, ts: TimeStamp, b: Boolean) -> Unit, time: TimeStamp, forward: Boolean){
            var firstTime = true

            if (recursive){
                file.walkTopDown().forEach{
                    if (firstTime) firstTime = false
                    else{
                        if (it.isFile && it.extension == "srt") fnFile(it, time, forward)
                        else inDirectory(it, recursive, fnFile, time, forward)
                    }
                }
            }else file.walkTopDown().forEach {
                if (firstTime) firstTime = false
                else{
                    if (it.isFile && it.extension == "srt") fnFile(it, time, forward)
                }
            }
        }

        fun removeAds(path: String, recursive: Boolean = false){
            val file = File(path)

            if (file.isFile) removeAdsFromFile(file)
            else inDirectory(file, recursive, ::removeAdsFromFile)
        }

        private fun shiftFile(file: File, time: TimeStamp, forward: Boolean){
            if (file.extension != "srt") throw SRTException("File is not from srt format")

            val subFile = SubtitleFile.SubtitleFileFrom(file)

            if (forward){
                subFile.subtitles.forEach { subtitle ->
                    subtitle.startTime += time
                    subtitle.endTime += time
                }
            }else{
                subFile.subtitles.forEach { subtitle ->
                    subtitle.startTime -= time
                    subtitle.endTime -= time
                }
            }

            subFile.save(file)
        }

        fun shift(path: String, time: TimeStamp, forward: Boolean, recursive: Boolean = false){
            val file = File(path)

            if (file.isFile) shiftFile(file, time, forward)
            else inDirectory(file, recursive, ::shiftFile, time, forward)
        }
    }
}