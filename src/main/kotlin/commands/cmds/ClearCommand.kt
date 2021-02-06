package commands.cmds

import commands.handling.Argument
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import utils.IMessage
import java.util.concurrent.TimeUnit

class ClearCommand : ICommand {
    override val info = CommandInfo(
        name = "Clear",
        invokes = listOf("clear", "clearMessage", "clearMessages"),
        args = listOf(
            Argument(type = "Amount (Number 1-99)", example = "eg. 69")
        ),
        description = "Clears the message amount specified",
    )

    override fun action(cmd: CommandContainer) {
        var i : Int = cmd.args[0]?.toInt()?: return
        for (message in cmd.channel.iterableHistory.cache(false)) {
            if (!message.isPinned) {
                message.delete().queueAfter(5, TimeUnit.SECONDS)
            }
            if (i-- <= 0) break
        }

        cmd.channel.sendMessage(
            IMessage(author = "Clear", subTitle = " ")
                .addLine(text = "Deleted ``${cmd.args[0]?.toInt()}`` messages.")
                .build()
        )
            .complete()
            .delete()
            .queueAfter(5, TimeUnit.SECONDS)
    }

}