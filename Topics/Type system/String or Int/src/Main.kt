fun isNumber(input: String): Any {
    return if (input.toIntOrNull() != null) input.toInt()
    else input
}