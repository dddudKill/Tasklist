class City(val name: String) {
    var degrees: Int = 0
        set(value) {
            field = if (value > 57 || value < -92) defaultValue else value
        }
        private val defaultValue = when (this.name) {
            "Dubai" -> 30
            "Hanoi" -> 20
            "Moscow" -> 5
            else -> 0
        }
}        

fun min(a: Int, b: Int) = if (a <= b) a else b

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai")
    firstCity.degrees = first
    val secondCity = City("Moscow")
    secondCity.degrees = second
    val thirdCity = City("Hanoi")
    thirdCity.degrees = third

    //implement comparing here
    val minTempFS = min(firstCity.degrees, secondCity.degrees)
    val minTempST = min(secondCity.degrees, thirdCity.degrees)
    val minTempFT = min(firstCity.degrees, thirdCity.degrees)

    if (minTempFS == minTempST && minTempFT == minTempFS) {
        println("neither")
        return
    }
    if (minTempFS < minTempST) {
        println(firstCity.name)
    } else if (minTempFS == minTempST) {
        println(secondCity.name)
    } else {
        println(thirdCity.name)
    }
}