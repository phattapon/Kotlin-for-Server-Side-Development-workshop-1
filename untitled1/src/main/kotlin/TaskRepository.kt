package com.example

import java.util.concurrent.atomic.AtomicInteger

object TaskRepository {
    private val tasks = mutableListOf<Task>()
    private val lastId = AtomicInteger(0)

    fun getAll(): List<Task> {
        return tasks.toList()
    }

    fun getById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun add(taskRequest: TaskRequest): Task {
        val newId = lastId.incrementAndGet()
        val newTask = Task(id = newId, content = taskRequest.content, isDone = taskRequest.isDone)
        tasks.add(newTask)
        return newTask
    }

    fun update(id: Int, updatedTaskRequest: TaskRequest): Task? {
        val index = tasks.indexOfFirst { it.id == id }
        return if (index != -1) {
            val existingTask = tasks[index]
            val updatedTask = existingTask.copy(
                content = updatedTaskRequest.content,
                isDone = updatedTaskRequest.isDone
            )
            tasks[index] = updatedTask
            updatedTask
        } else {
            null
        }
    }

    fun delete(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }

    fun populateSampleData() {
        tasks.clear()
        lastId.set(0)
        add(TaskRequest("Buy groceries", false))
        add(TaskRequest("Finish Ktor workshop", false))
        add(TaskRequest("Call mom", true))
    }
}