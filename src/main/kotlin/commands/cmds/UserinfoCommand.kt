package commands.cmds

import commands.handling.CommandInfo
import commands.handling.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.NationMember
import utils.IMessage

class UserinfoCommand : ICommand {
    override val info = CommandInfo(
        name = "Userinfo",
        invokes = listOf("Info", "Userinfo"),
        args = listOf("mentioned Members"),
        description = "Shows the commands you can use"
    )
    override fun action(args: List<String?>, event: MessageReceivedEvent?) {
        val message = IMessage(null,"Userinfo","Stored Info in DB")
        for(m in event!!.message.mentionedMembers){
            val nationMember = NationMember(m)
            message.addField(pName = m.effectiveName, pValue = "no DB connected", pInline = false)

            // TODO: send info about user instead of "No Database" as soon as db is
        }
        event.channel.sendMessage(message.build()).queue()
    }
}