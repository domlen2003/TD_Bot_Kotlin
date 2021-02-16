package commands.cmds

import commands.handling.CommandBehaviour
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import utils.Message

class TestCommand : CommandBehaviour() {
    override val info = CommandInfo(
        name = "Test",
        invokes = listOf("test", "tes"),
        description = "a test command"
    )

    override fun action(cmd: CommandContainer) {
        cmd.channel.sendMessage(Message(author = "Test", subTitle = "Reply to test command").build()).queue()
    }

}