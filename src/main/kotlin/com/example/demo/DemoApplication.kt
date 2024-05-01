package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

data class Message(val id: String?, val text: String)


// Tell Spring that MessageController is a REST controller,
@RestController
class MessageController {
    // Mark the function below that implement the endpoints corresponding to HTTP GET calls
    @GetMapping("/")
    // @RequestParam: Indicates that method parameter(name) should be bound to the web request parameter
    // http://localhost:8080/?name=<your-name>
    fun index(@RequestParam name: String) = "Hello $name"

    @GetMapping("/data")
    fun data() = listOf(
        Message("1", "Hello"),
        Message("2", "World"),
        Message("3", "Kotlin!"),
    )
}


fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
