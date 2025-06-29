package org.example

fun main() {
    while (true) {
        println("===== Unit Converter =====")
        println("โปรดเลือกหน่วยที่ต้องการแปลง:")
        println("1. Celsius to Fahrenheit")
        println("2. Kilometers to Miles")
        println("พิมพ์ 'exit' เพื่อออกจากโปรแกรม")
        print("เลือกเมนู (1, 2, or exit): ")

        val choice = readln()

        when (choice.lowercase()) {
            "1" -> convertCelsiusToFahrenheit()
            "2" -> convertKilometersToMiles()
            "exit" -> {
                println("ขอบคุณที่ใช้โปรแกรม. ลาก่อน!")
                return
            }
            else -> println("กรุณาเลือก 1, 2 หรือพิมพ์ exit เท่านั้น")
        }
        println() // เว้นบรรทัดว่าง
    }
}

fun celsiusToFahrenheit(celsius: Double): Double {
    return celsius * 9.0 / 5.0 + 32
}

fun kilometersToMiles(km: Double): Double {
    return km * 0.621371
}

fun convertCelsiusToFahrenheit() {
    print("ป้อนค่าองศาเซลเซียส (Celsius): ")
    val input = readln()
    val celsius = input.toDoubleOrNull() ?: run {
        println("ข้อมูลไม่ถูกต้อง กรุณาป้อนตัวเลขเท่านั้น")
        return
    }

    val fahrenheitResult = celsiusToFahrenheit(celsius)
    println("ผลลัพธ์: $celsius °C เท่ากับ ${"%.2f".format(fahrenheitResult)} °F")
}

fun convertKilometersToMiles() {
    print("ป้อนค่ากิโลเมตร (Kilometers): ")
    val input = readln()
    val kilometers = input.toDoubleOrNull() ?: run {
        println("ข้อมูลไม่ถูกต้อง กรุณาป้อนตัวเลขเท่านั้น")
        return
    }

    val milesResult = kilometersToMiles(kilometers)
    println("ผลลัพธ์: $kilometers km เท่ากับ ${"%.2f".format(milesResult)} miles")
}