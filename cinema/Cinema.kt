package cinema

fun printSeatsNumbers(seatsInRow: Int) {
    print("  ")
    for (index in 1..seatsInRow) {
        print("$index ")
    }
    println()
}

fun printSingleRow(cinemaRow: Array<String>, row: Int) {
    print("$row ")
    for (elem in cinemaRow) {
        print("$elem ")
    }
    println()
}

fun printCinema(seatsState: Array<Array<String>>) {
    println("Cinema:")
    printSeatsNumbers(seatsState.first().size)
    repeat(seatsState.size) {
        printSingleRow(seatsState[it], it + 1)
    }
    println()
}

fun printTicketPrice(price: Int, seats: Array<Array<String>>, seatInRow: Int, chosenRow: Int): Boolean {
    while (seats[chosenRow - 1][seatInRow - 1] == "S") {
            seats[chosenRow - 1][seatInRow - 1] = "B"
            println()
            println("Ticket price: \$$price")
            return true
        }
            println()
            println("That ticket has already been purchased!")
            return false
}

fun readNumber(message: String): Int {
    println(message)
    return readLine()!!.toInt()
}

fun getTicketPrice(rows: Int, seatsInRow: Int, chosenRow: Int): Int {
    if (rows * seatsInRow <= 60 || chosenRow <= rows / 2) {
        return 10
    } else {
        return 8
    }
}

fun totalIncome(rows: Int, seatsInRow: Int): Int {
    val seatsNumber: Int = rows * seatsInRow
    val total: Int = if (seatsNumber <= 60) {
        seatsNumber * 10
    } else {
        val frontSeats: Int = rows / 2 * seatsInRow
        val backSeats: Int = seatsNumber - frontSeats
        frontSeats * 10 + backSeats * 8
    }
    return total
}

fun printTotalIncome(totalIncome: Int) {
    val incomeMessage = "Total income: "
    print(incomeMessage)
    println("\$$totalIncome")
}

fun main() {
    val rows = readNumber("Enter the number of rows:")
    val seatsInRow = readNumber("Enter the number of seats in each row:")
    println()
    val seats = Array<Array<String>>(rows) { it -> Array<String>(seatsInRow) { "S" } }
    var numberTickets = 0
    var percentage = 0.00
    var currentIncome = 0
    var check = 1
    do {
        val menu = """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
         """.trimIndent()
        val answer = readNumber(menu)
        when (answer) {
            1 -> {
                printCinema(seats)
                println()
            }
            2 -> {
                while (numberTickets != check) {
                    try {
                        println()
                        val chosenRow = readNumber("Enter a row number:")
                        val seatInRow = readNumber("Enter a seat number in that row:")
                        if (printTicketPrice(
                                getTicketPrice(rows, seatsInRow, chosenRow),
                                seats,
                                seatInRow,
                                chosenRow
                            )
                        ) {
                            currentIncome += getTicketPrice(rows, seatsInRow, chosenRow)
                            numberTickets++
                            percentage = (100.00 * numberTickets.toDouble()) / (rows.toDouble() * seatsInRow.toDouble())
                        }
                        println()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        println()
                        println("Wrong input!")
                    }
                }
                check++
            }
            3 -> {
                println()
                println("Number of purchased tickets: $numberTickets")
                println("Percentage: ${String.format("%.2f", percentage)}%")
                println("Current income: $$currentIncome")
                printTotalIncome(totalIncome(rows, seatsInRow))
                println()
            }
        }
    } while (answer != 0)
}