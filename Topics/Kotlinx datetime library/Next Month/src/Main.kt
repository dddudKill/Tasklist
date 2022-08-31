import kotlinx.datetime.*

fun nextMonth(date: String): String {
    val months = DateTimePeriod(months = 1)
    return date.toInstant().plus(months, TimeZone.UTC).toString()
}

fun main() {
    val date = readln()
    println(nextMonth(date))
}