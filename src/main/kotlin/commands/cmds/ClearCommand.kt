package commands.cmds

import commands.handling.Argument
import commands.handling.CommandBehaviour
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import core.Bot
import utils.IMessage
import java.util.concurrent.TimeUnit

class ClearCommand(private val bot: Bot) : CommandBehaviour() {
    override val info = CommandInfo(
        name = "Clear",
        invokes = listOf("clear", "clearMessage", "clearMessages"),
        args = listOf(
            Argument(type = "Amount (Number 1-99)", example = "eg. 69", necessary = false)
        ),
        description = "Clears the message amount specified",
    )

    override fun action(cmd: CommandContainer) {

        val noArgs: Boolean
        var i: Int = if (cmd.args.isNotEmpty()) {
            noArgs = false
            cmd.args.first()!!.toInt()
        } else {
            noArgs = true
            1
        }
        for (message in cmd.channel.iterableHistory.cache(false)) {
            if (message.isPinned || ((message.author == bot.jda.selfUser) && noArgs)) continue
            message.delete().queue()
            if (i-- <= 0) break
        }

        cmd.channel.sendMessage(
            IMessage(author = "Clear", subTitle = " ")
                .addLine(
                    text = "Deleted ``${
                        if (!noArgs) {
                            cmd.args[0]!!.toInt()
                        } else {
                            "1"
                        }
                    }`` messages."
                )
                .build()
        )
            .complete()
            .delete()
            .queueAfter(3, TimeUnit.SECONDS)
    }

}