package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import core.Bot
import utils.IMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank
import java.util.*

class HelpCommand(private val bot: Bot) : ICommand {
    override val info = CommandInfo(name = "Help", invokes = listOf("Help", "Commands"), args = listOf(), description = "Shows the commands you can use")

    override fun action(args: Array<String?>, event: MessageReceivedEvent?) {
        val accessibleCommands: LinkedList<CommandInfo> = bot.commandHandler.listCommandinfos()
        accessibleCommands.removeIf { commandInfo1: CommandInfo ->
            !DiscordRank.findRank(event!!.member!!.roles[0].idLong).isAtLeast(commandInfo1.accessRank)
        }

        val reply = IMessage(bot = bot, title = "Help",subTitle = "Commands of the Bot.")
        for (cmd in accessibleCommands) reply.addField(cmd.name,cmd.description,false)

        event!!.channel.sendMessage(reply.build()).queue()
    }
}