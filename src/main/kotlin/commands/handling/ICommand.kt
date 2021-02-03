package commands.handling

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank

interface ICommand {
    val info: CommandInfo

    fun secure(args: Array<String?>, event: MessageReceivedEvent): Boolean {
        return  DiscordRank.findRank(event.member!!.roles[0].idLong).isAtLeast(info.accessRank)
    }

    fun action(args: Array<String?>, event: MessageReceivedEvent?)
}