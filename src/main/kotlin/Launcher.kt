package de.th3ph4nt0m.kotlinbot

import io.github.cdimascio.dotenv.dotenv
import kotlin.system.exitProcess
import de.th3ph4nt0m.kotlinbot.core.BotImpl as Bot

fun main(args: Array<String>) {

    val env = dotenv()
    val token = env["TOKEN"] ?: exitProcess(1)

    Bot(token, env)
}