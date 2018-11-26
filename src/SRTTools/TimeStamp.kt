package SRTTools

data class TimeStamp(var hours: Int, var minutes: Int, var seconds: Int, var milliseconds: Int){
    companion object {
        fun toFormat(time: String): List<Int>{
            val topPartsStr = time.split(",")
            val parts = topPartsStr[0].split(":").map { it.toInt() }
            val topParts = topPartsStr[1].map { it.toInt() }

            if (topPartsStr.size != 2) throw InvalidTimestampFormatException("$topParts")

            if (parts.size != 3) throw InvalidTimestampFormatException("$parts")
            return parts + topParts
        }
    }

    constructor(time: List<Int>): this(time[0], time[1], time[2], time[3])

    fun compile() = "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)},${String.format("%03d", milliseconds)}"
}