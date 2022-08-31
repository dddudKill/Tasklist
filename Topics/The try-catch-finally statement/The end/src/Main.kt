fun solution() {
    val x = readln().toInt()
    val y = readln().toInt()
    try {
        println(x / y)
    }
    catch (e: Exception) {
        println(e.message)
    }
    finally {
        println("This is the end, my friend.")
    }
}