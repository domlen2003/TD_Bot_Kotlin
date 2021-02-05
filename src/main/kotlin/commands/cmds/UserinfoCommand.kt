package commands.cmds

import commands.handling.Argument
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import utils.IMessage
import utils.NationMember

class UserinfoCommand : ICommand {
    override val info = CommandInfo(
        name = "Userinfo",
        invokes = listOf("Info", "Userinfo"),
        args = listOf(
            Argument(type = "MemberMentions", example = "@exampleMember"),
        ),
        description = "Shows the commands you can use"
    )

    override fun action(cmd: CommandContainer) {
        val message = IMessage(author = "Userinfo", subTitle = "Stored Info in DB")
        if (cmd.message.mentionedMembers.size <= 0) {
            argsProblemBreak(cmd)
            return
        }
        for (m in cmd.message.mentionedMembers) {
            val nationMember = NationMember(m)
            message.addField(name = m.effectiveName, value = "no DB connected", inline = false)

            // TODO: send info about user instead of "No Database" as soon as db is
        }
        cmd.channel.sendMessage(message.build()).queue()
    }
}