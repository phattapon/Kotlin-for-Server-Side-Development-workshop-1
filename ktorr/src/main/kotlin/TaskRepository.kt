package com.example

import java.util.concurrent.atomic.AtomicInteger

// --- 2. Data Layer (In-Memory Repository) ---
// object TaskRepository เพื่อจำลองฐานข้อมูลในหน่วยความจำ
// การใช้ 'object' หมายความว่าจะมีเพียง instance เดียวของ TaskRepository ในแอปพลิเคชัน
object TaskRepository {
    // ใช้ mutableListOf เพื่อเก็บ Task objects
    // 'private' ทำให้ tasks ไม่สามารถถูกแก้ไขได้โดยตรงจากภายนอก object นี้
    private val tasks = mutableListOf<Task>()

    // ใช้ AtomicInteger เพื่อสร้าง ID ที่ไม่ซ้ำกันอย่างปลอดภัยใน Multi-threaded environment
    // เริ่มต้น ID ที่ 0 และจะเพิ่มขึ้นเรื่อยๆ
    private val lastId = AtomicInteger(0)

    // ฟังก์ชันสำหรับดึง Task ทั้งหมด
    // toList() ใช้เพื่อคืนค่า List ที่ไม่สามารถแก้ไขภายนอกได้ (immutable copy)
    fun getAll(): List<Task> {
        return tasks.toList()
    }

    // ฟังก์ชันสำหรับดึง Task ตาม ID
    // คืนค่า Task object ถ้าพบ หรือ null ถ้าไม่พบ
    fun getById(id: Int): Task? {
        return tasks.find { it.id == id } // 'find' เป็นฟังก์ชันใน Kotlin collection ที่จะหา Element แรกที่ตรงเงื่อนไข
    }

    // ฟังก์ชันสำหรับเพิ่ม Task ใหม่ (รับ TaskRequest และสร้าง Task ที่มี ID)
    // คืนค่า Task ที่ถูกเพิ่มเข้าไปแล้ว
    fun add(taskRequest: TaskRequest): Task {
        val newId = lastId.incrementAndGet() // <-- แก้ไขตรงนี้: เปลี่ยน 'incrementAndget()' เป็น 'incrementAndGet()'
        // สร้าง Task object ใหม่ โดยใช้ ID ที่สร้างขึ้น และข้อมูลจาก TaskRequest
        val newTask = Task(id = newId, content = taskRequest.content, isDone = taskRequest.isDone)
        tasks.add(newTask) // เพิ่ม Task ใหม่เข้าไปใน List
        return newTask
    }

    // ฟังก์ชันสำหรับอัปเดต Task ที่มีอยู่
    // รับ ID ของ Task ที่ต้องการอัปเดต และ updatedTaskRequest (ข้อมูลใหม่)
    // คืนค่า Task ที่ถูกอัปเดตแล้ว หรือ null ถ้าไม่พบ Task ด้วย ID นั้น
    fun update(id: Int, updatedTaskRequest: TaskRequest): Task? {
        // หา index ของ Task ที่ต้องการอัปเดตใน List
        val index = tasks.indexOfFirst { it.id == id }
        return if (index != -1) { // ถ้าพบ Task (index ไม่เท่ากับ -1)
            val existingTask = tasks[index] // ดึง Task เดิมออกมา
            // สร้าง Task ใหม่โดยใช้ฟังก์ชัน copy() ของ data class
            // รักษา ID เดิมไว้ แต่เปลี่ยน content และ isDone ตามข้อมูลที่รับเข้ามา
            val updatedTask = existingTask.copy(
                content = updatedTaskRequest.content,
                isDone = updatedTaskRequest.isDone
            )
            tasks[index] = updatedTask // แทนที่ Task เดิมใน List ด้วย Task ที่อัปเดตแล้ว
            updatedTask // คืนค่า Task ที่อัปเดต
        } else {
            null // ไม่พบ Task ด้วย ID ที่ระบุ
        }
    }

    // ฟังก์ชันสำหรับลบ Task
    // รับ ID ของ Task ที่ต้องการลบ
    // คืนค่า true ถ้าลบสำเร็จ (พบ Task และลบออก) หรือ false ถ้าไม่พบ Task
    fun delete(id: Int): Boolean {
        // 'removeIf' เป็นฟังก์ชันใน MutableList ที่จะลบ Element ทั้งหมดที่ตรงเงื่อนไข
        // และคืนค่า true ถ้ามี Element อย่างน้อยหนึ่งตัวถูกลบ
        return tasks.removeIf { it.id == id }
    }

    // (Optional) ฟังก์ชันสำหรับ clear repository สำหรับการทดสอบ หรือเริ่มต้นใหม่
    fun clear() {
        tasks.clear() // ลบ Element ทั้งหมดออกจาก List
        lastId.set(0) // รีเซ็ต ID ให้เริ่มต้นใหม่จาก 0
    }

    // (Optional) ฟังก์ชันสำหรับเพิ่มข้อมูลเริ่มต้น (Sample data) เพื่อให้ทดสอบง่ายขึ้น
    fun populateSampleData() {
        clear() // ลบข้อมูลเก่าก่อน (เพื่อให้มั่นใจว่าข้อมูลเริ่มต้นไม่ซ้ำซ้อน)
        add(TaskRequest("Buy groceries", false))
        add(TaskRequest("Finish Ktor workshop", false))
        add(TaskRequest("Call mom", true))
    }
}
