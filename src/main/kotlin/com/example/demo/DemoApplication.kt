package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.jdbc.core.query
import java.util.*
import kotlin.jvm.optionals.toList

@SpringBootApplication
class DemoApplication

interface MessageRepository : CrudRepository<Message, String>

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)



// to implement the database access login within service layer, use @Service annotation
//@Service
//class MessageService(val db: JdbcTemplate) {
//    fun findMessages(): List<Message> = db.query("select * from messages") { response, _ ->
//        Message(response.getString("id"), response.getString("text"))
//    }
//
//    fun findMessageById(id: String): List<Message> =
//        db.query("select * from messages where id = ?", id) { response, _ ->
//            Message(response.getString("id"), response.getString("text"))
//        }
//
//    fun save(message: Message) {
//        val id = message.id ?: UUID.randomUUID().toString()
//        db.update(
//            "insert into messages values (? , ?)", id, message.text
//        )
//
//    }
//}
@Service
class MessageService(val db: MessageRepository) {
    fun findMessages(): List<Message> = db.findAll().toList()

    fun findMessageById(id: String): List<Message> = db.findById(id).toList()

    fun save(message: Message) {
        db.save(message)
    }
    fun <T: Any> Optional<out T>.toList(): List<T> =
        if(isPresent) listOf(get()) else emptyList()
}

// Tell Spring that MessageController is a REST controller,
@RestController
class MessageController(val service: MessageService) {
    // Mark the function below that implement the endpoints corresponding to HTTP GET calls
//    @GetMapping("/")
//    // @RequestParam: Indicates that method parameter(name) should be bound to the web request parameter
//    // http://localhost:8080/?name=<your-name>
//    fun index(@RequestParam name: String) = "Hello $name"

    //    @GetMapping("/data")
//    fun data() = listOf(
//        Message("1", "Hello"),
//        Message("2", "World"),
//        Message("3", "Kotlin!"),
//    )
    @GetMapping("/")
    fun index(): List<Message> = service.findMessages()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): List<Message> =
        service.findMessageById(id)

    // A method for handling HTTP POST requests needs to be annotated with @PostMapping
    // @RequestBody: Convert to Json sent as HTTP BODY content into an object.
    @PostMapping("/")
    fun post(@RequestBody message: Message) {
        service.save(message)
    }
}


fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)


}
