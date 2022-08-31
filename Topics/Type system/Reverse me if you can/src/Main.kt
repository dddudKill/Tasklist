fun reverse(input: Int?): Int {
    return if (input == null) {
        -1
    } else {
        var reverseInput = ""
        var value = input
        while (value > 0) {
            reverseInput += (value % 10).toString()
            value /= 10
        }
        reverseInput.toInt()
    }
}

fun main() {
    println(reverse(readlnOrNull()?.toIntOrNull()))
}