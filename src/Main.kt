fun main(args: Array<String>){
    val fnc = args[0]

    when (fnc){
        "ren" -> Renamer.start(args[1], args[2], args[3]) // Rename files in directory: ren path epPrefix newName
        "mov" -> Mover.start(args[1], args[2]) // Move the file from sourceUrl to destUrl: mov src dest
        "rad" -> { // Remove ads from srt file: rad [-r] path
            if (args.contains("-r")) SRTFixer.removeAds(args[2], true)
            else SRTFixer.removeAds(args[1])
        }
    }
}