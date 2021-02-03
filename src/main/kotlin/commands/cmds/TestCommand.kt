package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import core.Bot
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TestCommand(private val bot: Bot) : ICommand {
    override val info = CommandInfo(name = "Test", invokes = listOf("test", "tes"), args = listOf(), description = "a test command")

    override fun action(args: Array<String?>, event: MessageReceivedEvent?) {
        println()
    }

}