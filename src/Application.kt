package tlife.bot.dtcl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tlife.bot.dtcl.chat_bot.DtclChatBot

val port = System.getenv("PORT")?.toInt() ?: 23567

fun main() {
    embeddedServer(Netty, port) {
        module()
    }.start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {
    val jobBot = CoroutineScope(coroutineContext).launch {
        DtclChatBot(
            "5646290360:AAH-OEQtIwhDSKu-Fs0F7U33Edk3krUMyYU"
        ).setupBot()
    }

    routing {
        get("/"){
            call.respond("Hi, I'm Cóc Đại Ka. Developed by @thanhnh")
        }
        get("/stop"){
            if (jobBot.isActive){
                jobBot.cancel()
            }
            call.respond("Stopped job at ${System.currentTimeMillis()}")
        }
        get("/restart"){
            if (jobBot.isActive){
                jobBot.cancel()
            }
            jobBot.join()
            call.respond("Restarted job at ${System.currentTimeMillis()}")
        }
    }
}
