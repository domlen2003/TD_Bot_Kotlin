package commands.cmds

import commands.handling.CommandBehaviour
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import core.Bot
import security.DiscordRank
import utils.Message
import java.util.*

class HelpCommand(private val bot: Bot) : CommandBehaviour() {
    override val info = CommandInfo(
        name = "Help",
        invokes = listOf("Help", "Commands"),
        description = "Shows the commands you can use"
    )


    override fun action(cmd: CommandContainer) {
        val accessibleCommands: LinkedList<CommandInfo> = bot.commandHandler.listCommandInfos()
        accessibleCommands.removeIf { commandInfo1: CommandInfo ->
            !DiscordRank.findRank(cmd.member!!.roles[0].idLong)!!.isAtLeast(commandInfo1.accessRank)
        }
        val reply = Message(author = "Help", subTitle = "Commands of the Bot.")
        accessibleCommands.sortBy { commandInfo -> commandInfo.name }
        for (accessibleCmd in accessibleCommands) reply.addField(accessibleCmd.name, accessibleCmd.description, false).blankLine()

        cmd.channel.sendMessage(reply.build()).queue()
    }
}