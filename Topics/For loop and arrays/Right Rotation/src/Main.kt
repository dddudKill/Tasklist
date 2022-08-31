fun main() {
    val size = readln().toInt()
    val numbers = IntArray(size) { readln().toInt() }
    val shift = readln().toInt() % size
    for (i in 1..shift) {
        val tempLast = numbers.last()
        for (index in numbers.lastIndex downTo 1) {
            numbers[index] = numbers[index - 1]
        }
        numbers[0] = tempLast
    }
    println(numbers.joinToString(" "))
}