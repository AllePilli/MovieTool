import SRTTools.TimeStamp

val options = listOf("-r")

fun main(args: Array<String>){
    val fnc = args[0]
    val i = amtOfOptions(args)

    when (fnc){
        "prep" -> { // Rename file and remove ads: prep [-r] path epPrefix newName
            Renamer.start(args[1 + i], args[2 + i], args[3 + i])
            if (args.contains("-r")) SRTFixer.removeAds(args[2], true)
            else SRTFixer.removeAds(args[1])
        }
        "ren" -> Renamer.start(args[1], args[2], args[3]) // Rename files in directory: ren path epPrefix newName
        "mov" -> Mover.start(args[1], args[2]) // Move the file from sourceUrl to destUrl: mov src dest
        "rad" -> { // Remove ads from srt file: rad [-r] path
            if (args.contains("-r")) SRTFixer.removeAds(args[2], true)
            else SRTFixer.removeAds(args[1])
        }
        "sh" -> { // sh [-r] path [-]00:00:00,000
            var recursive = false
            if (args.contains("-r")) recursive = true

            if (args[2 + i].contains("-")) SRTFixer.shift(args[1 + i], TimeStamp(TimeStamp.toFormat(args[2 + i].substring(1, args[2 + i].length))), false, recursive)
            else SRTFixer.shift(args[1 + i], TimeStamp(TimeStamp.toFormat(args[2 + i])), true, recursive)
        }
    }
}

private fun amtOfOptions(args: Array<String>): Int{
    var amt = 0
    options.forEach { opt -> if (args.contains(opt)) amt++ }
    return amt
}