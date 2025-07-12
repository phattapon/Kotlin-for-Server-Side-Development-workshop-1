package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    TaskRepository.populateSampleData()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureRouting() {
    routing {
        get("/tasks") {
            call.respond(TaskRepository.getAll())
        }

        get("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@get
            }
            val task = TaskRepository.getById(id)
            if (task == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(task)
            }
        }

        post("/tasks") {
            val taskRequest = call.receive<TaskRequest>()
            val newTask = TaskRepository.add(taskRequest)
            call.respond(HttpStatusCode.Created, newTask)
        }

        put("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@put
            }
            val updatedTaskRequest = call.receive<TaskRequest>()
            val updatedTask = TaskRepository.update(id, updatedTaskRequest)
            if (updatedTask == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(updatedTask)
            }
        }

        delete("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@delete
            }
            val removed = TaskRepository.delete(id)
            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}