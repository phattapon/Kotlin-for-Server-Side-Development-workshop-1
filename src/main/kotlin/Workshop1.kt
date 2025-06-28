package org.example

// Workshop #1: Simple Console Application - Unit Converter

fun main() {
    while (true) { // ลูปนี้จะทำงานเรื่อยๆ จนกว่าจะเจอคำสั่ง 'break'
        // 1. แสดงเมนูให้ผู้ใช้เลือก
        println("===== Unit Converter =====")
        println("โปรดเลือกหน่วยที่ต้องการแปลง:")
        println("1. Celsius to Fahrenheit")
        println("2. Kilometers to Miles")
        println("พิมพ์ 'exit' เพื่อออกจากโปรแกรม")
        print("เลือกเมนู (1, 2, or exit): ")

        // 2. รับข้อมูลตัวเลือกจากผู้ใช้ (ต้องอยู่ภายในลูปเพื่อให้รับค่าได้ซ้ำๆ)
        val choice = readln()

        // 3. ควบคุมการทำงานด้วย when expression
        when (choice.lowercase()) { // ใช้ .lowercase() เพื่อให้ไม่สนใจตัวพิมพ์เล็กพิมพ์ใหญ่สำหรับ 'exit'
            "1" -> {
                convertCelsiusToFahrenheit() // เรียกใช้ฟังก์ชันจัดการการแปลง Celsius to Fahrenheit
            }
            "2" -> {
                convertKilometersToMiles() // เรียกใช้ฟังก์ชันจัดการการแปลง Kilometers to Miles
            }
            "exit" -> {
                // เลือก 'exit' เพื่อออกจากโปรแกรม
                println("ออกจากโปรแกรม")
                break // คำสั่ง 'break' นี้จะทำให้ลูป while หยุดทำงาน
            }
            else -> { // ตัวเลือกอื่นๆ ที่ไม่ถูกต้อง
                println("ตัวเลือกไม่ถูกต้อง กรุณาเลือก 1, 2 หรือ 'exit'")
            }
        }
        println() // เพิ่มบรรทัดว่างเพื่อให้ผลลัพธ์ของแต่ละรอบดูง่ายขึ้น
    }
}

// 4. สร้างฟังก์ชันแยกสำหรับการแปลงหน่วย Celsius to Fahrenheit
// สูตร celsius * 9.0 / 5.0 + 32
fun celsiusToFahrenheit(celsius: Double): Double {
    return celsius * 9.0 / 5.0 + 32
}

// 4. สร้างฟังก์ชันแยกสำหรับการแปลงหน่วย Kilometers to Miles
// สูตร kilometers * 0.621371
fun kilometersToMiles(kilometers: Double): Double {
    return kilometers * 0.621371
}

// ฟังก์ชันสำหรับจัดการกระบวนการแปลง Celsius to Fahrenheit ทั้งหมด
fun convertCelsiusToFahrenheit() {
    print("ป้อนค่าองศาเซลเซียส (Celsius): ")
    val input = readln() // รับค่าจากผู้ใช้

    // 5. จัดการ Null Safety ด้วย toDoubleOrNull() และ Elvis operator (?:)
    // ออกจากฟังก์ชัน convertCelsiusToFahrenheit() หากข้อมูลผิดพลาด: return
    val celsius = input.toDoubleOrNull() ?: run {
        println("อินพุตไม่ถูกต้อง กรุณาป้อนตัวเลขสำหรับอุณหภูมิ")
        return // ออกจากฟังก์ชัน convertCelsiusToFahrenheit ทันที
    }

    val fahrenheitResult = celsiusToFahrenheit(celsius) // เรียกใช้ฟังก์ชันแปลงหน่วย

    // 6. แสดงผลลัพธ์
    // ใช้ String format เพื่อแสดงทศนิยม 2 ตำแหน่ง
    println("ผลลัพธ์: $celsius °C เท่ากับ ${"%.2f".format(fahrenheitResult)} °F")
}

// ฟังก์ชันสำหรับจัดการกระบวนการแปลง Kilometers to Miles ทั้งหมด
fun convertKilometersToMiles() {
    print("ป้อนค่ากิโลเมตร (Kilometers): ")
    val input = readln() // รับค่าจากผู้ใช้

    // 5. จัดการ Null Safety ด้วย toDoubleOrNull() และ Elvis operator (?:)
    val kilometers = input.toDoubleOrNull() ?: run {
        println("อินพุตไม่ถูกต้อง กรุณาป้อนตัวเลขสำหรับระยะทาง")
        return // ออกจากฟังก์ชัน convertKilometersToMiles ทันที
    }

    val milesResult = kilometersToMiles(kilometers) // เรียกใช้ฟังก์ชันแปลงหน่วย

    // 6. แสดงผลลัพธ์
    // ใช้ String format เพื่อแสดงทศนิยม 2 ตำแหน่ง
    println("ผลลัพธ์: $kilometers km เท่ากับ ${"%.2f".format(milesResult)} miles")
}