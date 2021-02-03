import io.github.cdimascio.dotenv.dotenv
import kotlin.system.exitProcess
import core.BotImpl as Bot

fun main(args: Array<String>) {
    val token = dotenv()["TOKEN"] ?: exitProcess(1)

    Bot(token)
}