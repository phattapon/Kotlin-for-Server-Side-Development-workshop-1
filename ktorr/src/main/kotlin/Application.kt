package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.* // ตรวจสอบว่ามีบรรทัดนี้!
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*

// Main entry point for your Ktor application.
// This function is typically called by the Ktor server engine (like Netty).
fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

// This is the main module for your Ktor application.
// It's an extension function of 'Application' where you configure your server.
fun Application.module() {
    // 1. Configure JSON Serialization
    // This enables Ktor to automatically convert Kotlin objects to JSON
    // and JSON requests to Kotlin objects using @Serializable data classes.
    configureSerialization()

    configureRouting()

    // Optional: Populate some sample data when the application starts
    // This helps in testing the API immediately after launch.
    TaskRepository.populateSampleData()
}

// Extension function to encapsulate ContentNegotiation setup.
// It tells Ktor to use JSON for content negotiation based on kotlinx.serialization.
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json() // Use the default JSON configuration from kotlinx.serialization
    }
}

// Extension function to encapsulate all API route definitions.
// This is where you implement the logic for each HTTP endpoint.
fun Application.configureRouting() {
    routing {
        // GET /tasks: Retrieve all tasks
        // Returns a list of all tasks from the repository with HTTP status 200 OK.
        get("/tasks") {
            val tasks = TaskRepository.getAll()
            call.respond(HttpStatusCode.OK, tasks)
        }

        // GET /tasks/{id}: Retrieve a single task by ID
        // Looks for a task by its ID. If found, returns it with 200 OK.
        // If not found, returns 404 Not Found.
        get("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() // Extract ID from path, convert to Int, handle null/invalid
            if (id == null) {
                // If ID is missing or not a valid integer, respond with 400 Bad Request.
                call.respond(HttpStatusCode.BadRequest, "Invalid Task ID format. ID must be an integer.")
                return@get // Exit the current route handler
            }

            val task = TaskRepository.getById(id) // Try to get the task from the repository
            if (task != null) {
                // If task is found, respond with 200 OK and the task object.
                call.respond(HttpStatusCode.OK, task)
            } else {
                // If task is not found, respond with 404 Not Found.
                call.respond(HttpStatusCode.NotFound, "Task with ID $id not found.")
            }
        }

        // POST /tasks: Create a new task
        // Receives a TaskRequest object from the request body, adds it to the repository,
        // and responds with the newly created Task object and HTTP status 201 Created.
        post("/tasks") {
            // Use call.receive<TaskRequest>() to deserialize the JSON request body into a TaskRequest object.
            val taskRequest = try {
                call.receive<TaskRequest>()
            } catch (e: Exception) {
                // Handle cases where the request body is malformed or missing required fields.
                call.respond(HttpStatusCode.BadRequest, "Invalid request body. Ensure 'content' and 'isDone' are provided.")
                return@post
            }

            val newTask = TaskRepository.add(taskRequest) // Add the new task to the repository
            call.respond(HttpStatusCode.Created, newTask) // Respond with 201 Created and the new task
        }

        // PUT /tasks/{id}: Update an existing task
        // Receives an ID and updated task data (TaskRequest). If the task exists, it's updated.
        // Responds with 200 OK if updated, or 404 Not Found if the task ID doesn't exist.
        put("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid Task ID format. ID must be an integer.")
                return@put
            }

            val updatedTaskRequest = try {
                call.receive<TaskRequest>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request body. Ensure 'content' and 'isDone' are provided.")
                return@put
            }

            val updatedTask = TaskRepository.update(id, updatedTaskRequest) // Attempt to update the task
            if (updatedTask != null) {
                call.respond(HttpStatusCode.OK, updatedTask) // Respond with 200 OK and the updated task
            } else {
                call.respond(HttpStatusCode.NotFound, "Task with ID $id not found for update.")
            }
        }

        // DELETE /tasks/{id}: Delete a task
        // Receives an ID. If the task is found and deleted, responds with 204 No Content.
        // If not found, responds with 404 Not Found.
        delete("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid Task ID format. ID must be an integer.")
                return@delete
            }

            if (TaskRepository.delete(id)) {

                call.respond(HttpStatusCode.NoContent)
            } else {
                // If task was not found (and thus not deleted), respond with 404 Not Found.
                call.respond(HttpStatusCode.NotFound, "Task with ID $id not found for deletion.")
            }
        }
    }
}
