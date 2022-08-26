package tlife.bot.dtcl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tlife.bot.dtcl.chat_bot.DtclChatBot


fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    val host = "127.0.0.1"
    embeddedServer(Netty, port = port , host = host) {
        module()
    }.start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    routing {
        get("/"){
            call.respond("Hi, I'm Cóc Đại Ka. Developed by @thanhnh")
        }
        get("/stop"){
            call.respond("Stopped job at ${System.currentTimeMillis()}")
        }
        get("/start"){
            CoroutineScope(coroutineContext).launch {
                DtclChatBot(
                    "5646290360:AAH-OEQtIwhDSKu-Fs0F7U33Edk3krUMyYU"
                ).setupBot()
            }
            call.respond("started job at ${System.currentTimeMillis()}")
        }
    }
}
