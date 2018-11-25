class Functions{
    companion object {
        fun prompt(msg: String): String{
            print("$msg =>")
            return readLine() ?: ""
        }
    }
}