val lambda: (Long, Long) -> Long = {  leftBorder: Long, rightBorder: Long ->
    var product = 1L
    for (i in leftBorder..rightBorder) product *= i
    product
}