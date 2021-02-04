package commands.handling

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank

interface ICommand {
    val info: CommandInfo

    fun secure(args: List<String?>, event: MessageReceivedEvent): Boolean {
        if(info.args.size>args.size){println("missing args")}//TODO: add action for missing args
        return  DiscordRank.findRank(event.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)
    }

    fun action(args: List<String?>, event: MessageReceivedEvent?)
}