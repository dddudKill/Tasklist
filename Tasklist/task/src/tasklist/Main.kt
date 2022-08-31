package tasklist

import kotlinx.datetime.*
import java.io.File
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class Task(var date: String, var time: String, var priority: String, var dueTag: String, var task: MutableList<String>)

class Menu {
    private var taskList = mutableListOf<Task>()

    private fun inputPriority(): String {
        var inputPriority: String
        while (true) {
            println("Input the task priority (C, H, N, L):")
            inputPriority = when (readln().uppercase()) {
                "C" -> "C"
                "H" -> "H"
                "N" -> "N"
                "L" -> "L"
                else -> ""
            }
            if (inputPriority != "") break
        }
        return inputPriority
    }

    private fun inputDate(): String {
        var inputDate = ""
        while (true) {
            println("Input the date (yyyy-mm-dd):")
            try {
                val inputDateToCheck = readln()
                if (!"\\d{4}-\\d\\d?-\\d\\d?".toRegex()
                        .matches(inputDateToCheck)
                ) throw Exception("IllegalArgumentException")
                val yearsMonthsDays = inputDateToCheck.split("-")
                val date = LocalDate(yearsMonthsDays[0].toInt(), yearsMonthsDays[1].toInt(), yearsMonthsDays[2].toInt())
                inputDate = date.toString()
            } catch (e: Exception) {
                println("The input date is invalid")
            }
            if (inputDate != "") break
        }
        return inputDate
    }

    private fun inputTime(): String {
        var inputTime = ""
        while (true) {
            println("Input the time (hh:mm):")
            try {
                val inputTimeToCheck = readln()
                if ("0:0".toRegex().matches(inputTimeToCheck)) return "00:00"
                if (!"\\d?\\d:\\d\\d".toRegex().matches(inputTimeToCheck)) throw Exception("IllegalArgumentException")
                val hours = if (inputTimeToCheck.split(":").first().toInt() in 0..9 && !"0\\d".toRegex().matches(inputTimeToCheck.split(":").first())) "0${inputTimeToCheck.split(":").first()}" else inputTimeToCheck.split(":").first()
                val minutes = if (inputTimeToCheck.split(":").last() == "0") "00" else inputTimeToCheck.split(":").last()
                if (hours !in "0".."23" || minutes !in "0".."59") throw Exception("IllegalArgumentException")
                inputTime = "$hours:$minutes"
            } catch (e: Exception) {
                println("The input time is invalid")
            }
            if (inputTime != "") break
        }
        return inputTime
    }

    private fun inputTask(): MutableList<String> {
        println("Input a new task (enter a blank line to end):")
        val inputTask = mutableListOf<String>()
        while (true) {
            val input = readln().trim()
            inputTask.add(input)
            if (inputTask.first() == "") {
                println("The task is blank")
                return inputTask
            } else if (input == "") {
                break
            }
        }
        if (inputTask.first() != "") inputTask.remove("")
        return inputTask
    }

    private fun calculateDue(date: String): String {
        val yearsMonthsDays = date.split("-")
        val taskDate = LocalDate(yearsMonthsDays[0].toInt(), yearsMonthsDays[1].toInt(), yearsMonthsDays[2].toInt())
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
        return when (currentDate.daysUntil(taskDate)) {
            0 -> "T"
            in 1..Int.MAX_VALUE -> "I"
            in Int.MIN_VALUE..-1 -> "O"
            else -> ""
        }
    }

    fun addTask(inputPriority: String = inputPriority(), inputDate: String = inputDate(), inputTime: String = inputTime(), inputTask: MutableList<String> = inputTask(), dueTag: String = calculateDue(inputDate)) {
        if (inputTask.first() != "") taskList.add(Task(inputDate, inputTime, inputPriority, dueTag, inputTask))
    }

    fun print() {
        if (taskList.isEmpty()) {
            println("No tasks have been input")
            return
        }

        fun printLineSeparator() {
            println("+----+------------+-------+---+---+--------------------------------------------+")
        }
        fun printHeader() {
            printLineSeparator()
            println("| N  |    Date    | Time  | P | D |                   Task                     |")
            printLineSeparator()
        }
        fun printNumber(i: Int) {
            if (i in 1..9) print("| $i  |")
            else print("| $i |")
        }
        fun printDate(i: Int) {
            print(" ${taskList[i - 1].date} |")
        }
        fun printTime(i: Int) {
            print(" ${taskList[i - 1].time} |")
        }
        fun printPriority(i: Int) {
            when (taskList[i - 1].priority) {
                "C" -> print(" \u001B[101m \u001B[0m |")
                "H" -> print(" \u001B[103m \u001B[0m |")
                "N" -> print(" \u001B[102m \u001B[0m |")
                "L" -> print(" \u001B[104m \u001B[0m |")
            }
        }
        fun printDueTag(i: Int) {
            when (taskList[i - 1].dueTag) {
                "I" -> print(" \u001B[102m \u001B[0m |")
                "T" -> print(" \u001B[103m \u001B[0m |")
                "O" -> print(" \u001B[101m \u001B[0m |")
            }
        }
        fun printEmpty() {
            println()
            print("|    |            |       |   |   |")
        }
        fun printTask(i: Int) {
            for (j in taskList[i - 1].task.indices) {
                for (charNumber in taskList[i - 1].task[j].indices){
                    if (charNumber % 44 == 0 && charNumber != 0) printEmpty()
                    print("${taskList[i - 1].task[j][charNumber]}")
                    if ((charNumber + 1) % 44 == 0) print("|")
                }
                for (spaceNumber in 1..((44 - taskList[i - 1].task[j].length % 44) % 44)) print(" ")
                if (taskList[i - 1].task[j].length % 44 != 0) print("|")
                if (j + 1 in taskList[i - 1].task.indices) printEmpty()
            }
            println()
        }

        printHeader()
        for (i in 1..taskList.size) {
            printNumber(i)
            printDate(i)
            printTime(i)
            printPriority(i)
            printDueTag(i)
            printTask(i)
            printLineSeparator()
        }
    }

    fun edit() {
        print()
        if (taskList.isEmpty()) return
        val edit: Int
        while (true) {
            println("Input the task number (1-${taskList.size}):")
            val editInput = readln()
            if (editInput in "1"..taskList.size.toString()) {
                edit = editInput.toInt()
                break
            }
            println("Invalid task number")
        }
        while (true) {
            println("Input a field to edit (priority, date, time, task):")
            when (readln()) {
                "priority" -> { taskList[edit - 1].priority = inputPriority(); break }
                "date" -> { val date = inputDate(); taskList[edit - 1].date = date; taskList[edit - 1].dueTag = calculateDue(date); break }
                "time" -> { taskList[edit - 1].time = inputTime(); break }
                "task" -> { taskList[edit - 1].task = inputTask(); break }
                else -> println("Invalid field")
            }
        }
        println("The task is changed")
    }

    fun delete() {
        print()
        if (taskList.isEmpty()) return
        val delete: Int
        while (true) {
            println("Input the task number (1-${taskList.size}):")
            val deleteInput = readln()
            if (deleteInput in "1"..taskList.size.toString()) {
                delete = deleteInput.toInt()
                break
            }
            println("Invalid task number")
        }
        taskList.removeAt(delete - 1)
        println("The task is deleted")
    }

    fun end() {
        println("Tasklist exiting!")
    }

    fun createJsonFile(file: File) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(MutableList::class.java, Task::class.java)
        val taskListAdapter = moshi.adapter<List<Task?>>(type)
        file.writeText(taskListAdapter.toJson(taskList))
    }

    fun readFile(file: File) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(MutableList::class.java, Task::class.java)
        val taskListAdapter = moshi.adapter<List<Task?>>(type)
        val jsonStr = file.readText()
        val taskListFromJson = taskListAdapter.fromJson(jsonStr)!!
        for (task in taskListFromJson) {
            if (task != null) {
                addTask(task.priority, task.date, task.time, task.task, task.dueTag)
            }
        }
    }
}



fun main() {
    val menu = Menu()
    val jsonFile = File("tasklist.json")
    if (jsonFile.exists()) {
        menu.readFile(jsonFile)
    }
    while (true) {
        println("Input an action (add, print, edit, delete, end):")
        when (readln().trim().lowercase()) {
            "add" -> menu.addTask()
            "print" -> menu.print()
            "edit" -> menu.edit()
            "delete" -> menu.delete()
            "end" -> {
                menu.end()
                menu.createJsonFile(jsonFile)
                break
            }
            else -> println("The input action is invalid")
        }
    }
}