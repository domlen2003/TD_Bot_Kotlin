package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import core.Bot
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.IMessage

class TestCommand(private val bot: Bot) : ICommand {
    override val info = CommandInfo(
        name = "Test",
        invokes = listOf("test", "tes"),
        args = listOf(),
        description = "a test command"
    )

    override fun action(args: List<String?>, event: MessageReceivedEvent?) {
        event!!.channel.sendMessage(IMessage(title = "Test", subTitle = "Reply to test command").build()).queue()
    }

}