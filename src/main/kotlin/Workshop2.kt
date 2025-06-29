package org.example

// 1. กำหนด data class สำหรับเก็บข้อมูลสินค้า
data class Product(val name: String, val price: Double, val category: String)

// ✅ ฟังก์ชันสำหรับให้ระบบนำไปทดสอบ
fun calculateTotalElectronicsPriceOver500(products: List<Product>): Double {
    return products
        .filter { it.category == "Electronics" && it.price > 500.0 }
        .sumOf { it.price }
}

fun countElectronicsOver500(products: List<Product>): Int {
    return products.count { it.category == "Electronics" && it.price > 500.0 }
}

fun main() {
    // 2. สร้างรายการสินค้าตัวอย่าง (List<Product>)
    val products = listOf(
        Product("Laptop", 35000.0, "Electronics"),
        Product("Smartphone", 25000.0, "Electronics"),
        Product("T-shirt", 450.0, "Apparel"),
        Product("Monitor", 7500.0, "Electronics"),
        Product("Keyboard", 499.0, "Electronics"), // ราคาไม่เกิน 500
        Product("Jeans", 1200.0, "Apparel"),
        Product("Headphones", 1800.0, "Electronics") // ตรงตามเงื่อนไข
    )

    println("รายการสินค้าทั้งหมด:")
    products.forEach { println(it) }
    println("--------------------------------------------------")

    // 3. วิธีที่ 1: การใช้ Chaining กับ List โดยตรง
    val totalElecPriceOver500 = products
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .sum()

    println("วิธีที่ 1: ใช้ Chaining กับ List")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500 บาท")
    println("--------------------------------------------------")

    // 4. (ขั้นสูง) วิธีที่ 2: การใช้ .asSequence() เพื่อเพิ่มประสิทธิภาพ
    val totalElecPriceOver500Sequence = products
        .asSequence()
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .sum()

    println("วิธีที่ 2: ใช้ .asSequence() (ขั้นสูง)")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500Sequence บาท")
    println("--------------------------------------------------")

    // ✅ แสดงผลลัพธ์จากฟังก์ชันที่เตรียมไว้สำหรับการทดสอบ
    val resultFromFunction = calculateTotalElectronicsPriceOver500(products)
    val countFromFunction = countElectronicsOver500(products)

    println("ผลรวมจากฟังก์ชัน calculateTotalElectronicsPriceOver500(): $resultFromFunction บาท")
    println("จำนวนสินค้าจากฟังก์ชัน countElectronicsOver500(): $countFromFunction ชิ้น")
    println("--------------------------------------------------")

    // 5. จัดกลุ่มสินค้าตามช่วงราคา
    val groupedByPriceRange = products.groupBy { product ->
        when {
            product.price <= 1000 -> "ไม่เกิน 1,000 บาท"
            product.price in 1000.0..9999.0 -> "1,000 - 9,999 บาท"
            else -> "10,000 บาทขึ้นไป"
        }
    }

    println("กลุ่มสินค้าตามช่วงราคา:")
    for ((range, group) in groupedByPriceRange) {
        println("- $range:")
        group.forEach { println("  • ${it.name} (${it.price} บาท)") }
    }

}