package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import core.Bot
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank
import utils.IMessage
import java.util.*

class HelpCommand(private val bot: Bot) : ICommand {
    override val info = CommandInfo(
        name = "Help",
        invokes = listOf("Help", "Commands"),
        args = listOf(),
        description = "Shows the commands you can use"
    )


    override fun action(args: List<String?>, event: MessageReceivedEvent?) {
        val accessibleCommands: LinkedList<CommandInfo> = bot.commandHandler.listCommandinfos()
        accessibleCommands.removeIf { commandInfo1: CommandInfo ->
            !DiscordRank.findRank(event!!.member!!.roles[0].idLong)!!.isAtLeast(commandInfo1.accessRank)
        }
        val reply = IMessage(title = "Help", subTitle = "Commands of the Bot.")
        accessibleCommands.sortBy { commandInfo -> commandInfo.name }
        for (cmd in accessibleCommands) reply.addField(cmd.name, cmd.description, false)

        event!!.channel.sendMessage(reply.build()).queue()
    }
}