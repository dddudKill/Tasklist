fun main() {
    val size = readln().toInt()
    val numbers = IntArray(size) { readln().toInt() }
    var count = 0
    for (index in 0..numbers.lastIndex - 2) {
        if (numbers[index + 2] - 1 == numbers[index + 1] && numbers[index + 1] - 1 == numbers[index]) {
            count += 1
        }
    }
    println(count)
}