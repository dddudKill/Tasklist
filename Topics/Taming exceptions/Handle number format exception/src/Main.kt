fun parseCardNumber(cardNumber: String): Long {
    val regex = Regex("\\d{4} \\d{4} \\d{4} \\d{4}")
    if (regex.matches(cardNumber)) {
        return cardNumber.replace("\\s".toRegex(), "").toLong()
    } else {
        throw Exception("Wrong number!")
    }
}
/*
fun main() {
    parseCardNumber("1234 5678 9012 3456")
}*/
