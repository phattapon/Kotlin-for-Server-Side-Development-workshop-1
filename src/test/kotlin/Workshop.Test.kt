import kotlin.test.Test
import kotlin.test.assertEquals

// ดึงฟังก์ชันจาก Workshop1 และ Workshop2
import org.example.celsiusToFahrenheit
import org.example.kilometersToMiles
import org.example.Product
import org.example.calculateTotalElectronicsPriceOver500
import org.example.countElectronicsOver500

class WorkshopTest {

    // --- Tests for Workshop #1: Unit Converter ---

    // celsius input: 20.0
    // expected output: 68.0
    @Test
    fun `test celsiusToFahrenheit with positive value`() {
        val celsiusInput = 20.0
        val expectedFahrenheit = 68.0
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "20°C should be 68°F")
    }

    // celsius input: 0.0
    // expected output: 32.0
    @Test
    fun `test celsiusToFahrenheit with zero`() {
        val celsiusInput = 0.0
        val expectedFahrenheit = 32.0
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "0°C should be 32°F")
    }

    // celsius input: -10.0
    // expected output: 14.0
    @Test
    fun `test celsiusToFahrenheit with negative value`() {
        val celsiusInput = -10.0
        val expectedFahrenheit = 14.0
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "-10°C should be 14°F")
    }

    // kilometers input: 1.0
    // expected output: 0.621371
    @Test
    fun `test kilometersToMiles with one kilometer`() {
        val kilometerInput = 1.0
        val expectedMiles = 0.621371
        val actualMiles = kilometersToMiles(kilometerInput)
        assertEquals(expectedMiles, actualMiles, 0.000001, "1 km should be 0.621371 miles")
    }

    // --- Tests for Workshop #1: Unit Converter End ---


    // --- Tests for Workshop #2: Data Analysis Pipeline ---

    @Test
    fun `test calculateTotalElectronicsPriceOver500 returns correct sum`() {
        val products = listOf(
            Product("Laptop", 1200.0, "Electronics"),
            Product("TV", 800.0, "Electronics"),
            Product("USB Cable", 100.0, "Electronics"),
            Product("Notebook", 50.0, "Stationery")
        )
        val expectedTotal = 2000.0
        val actualTotal = calculateTotalElectronicsPriceOver500(products)
        assertEquals(expectedTotal, actualTotal, 0.001)
    }

    @Test
    fun `test countElectronicsOver500 returns correct count`() {
        val products = listOf(
            Product("Smartphone", 600.0, "Electronics"),
            Product("Monitor", 1000.0, "Electronics"),
            Product("Mouse", 250.0, "Electronics"),
            Product("Pen", 30.0, "Stationery")
        )
        val expectedCount = 2
        val actualCount = countElectronicsOver500(products)
        assertEquals(expectedCount, actualCount)
    }

    // --- Tests for Workshop #2: Data Analysis Pipeline End ---
}