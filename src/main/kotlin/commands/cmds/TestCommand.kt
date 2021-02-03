package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TestCommand : ICommand{
    override val info = CommandInfo(name = "Test", invokes = listOf("test","tes"),args = listOf())

    override fun action(args: Array<String?>, event: MessageReceivedEvent?) {
        println("succeeded")
    }

}