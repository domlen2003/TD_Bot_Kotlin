package commands.cmds

import commands.handling.Argument
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import net.dv8tion.jda.api.entities.Message
import utils.IMessage
import java.util.*
import java.util.concurrent.TimeUnit

class ClearCommand : ICommand {
    override val info = CommandInfo(
        name = "Clear",
        invokes = listOf("clear", "clearMessage", "clearMessages"),
        args = listOf(
            Argument(type = "Amount (Number 1-99)", example = "eg. 69")
        ),
        description = "Clears the message amount specified",
        argsNecessary = false
    )

    override fun action(cmd: CommandContainer) {
        val messages: LinkedList<Message> = LinkedList()
        var i =
            if(cmd.args.isNotEmpty()) cmd.args[0]!!.toInt()
            else 2
        for (message in cmd.channel.iterableHistory.cache(false)) {
            if (!message.isPinned) {
                messages.add(message)
            }
            if (i-- <= 0) break
        }
        cmd.channel.purgeMessages(messages)
        cmd.channel.sendMessage(
            IMessage(author = "Clear", subTitle = " ")
                .addLine(text = "Deleted ``${messages.size - 1}`` messages.")
                .build()
        )
            .complete()
            .delete()
            .queueAfter(5, TimeUnit.SECONDS)
    }

}