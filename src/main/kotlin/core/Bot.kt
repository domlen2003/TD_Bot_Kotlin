package core

import commands.handling.CommandHandler
import net.dv8tion.jda.api.JDA

interface Bot {
    /**
     * The [JDA] instance
     */
    val jda: JDA

    /**
     * The [CommandHandler] instance
     */
    val commandHandler: CommandHandler
}