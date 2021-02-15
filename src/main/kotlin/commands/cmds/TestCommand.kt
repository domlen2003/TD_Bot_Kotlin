package commands.cmds

import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import utils.IMessage

class TestCommand : ICommand {
    override val info = CommandInfo(
        name = "Test",
        invokes = listOf("test", "tes"),
        args = listOf(),
        description = "a test command"
    )

    override fun action(cmd: CommandContainer) {
        cmd.channel.sendMessage(IMessage(author = "Test", subTitle = "Reply to test command").build()).queue()
    }

}