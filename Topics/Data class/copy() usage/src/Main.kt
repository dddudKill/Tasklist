// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)

fun main() {
    val height = readln().toInt()
    val length = readln().toInt()
    val lenght1 = readln().toInt()
    val width = readln().toInt()

    val box1 = Box(height, length, width)
    val box2 = box1.copy(length = lenght1)
    println(box1)
    println(box2)
}