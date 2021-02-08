package commands.cmds

import commands.handling.Argument
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import commands.handling.ICommand
import commands.handling.LimitationReason
import security.DiscordRank
import utils.IMessage
import utils.NationMemberImpl

class UserinfoCommand : ICommand {
    override val info = CommandInfo(
        name = "Userinfo",
        invokes = listOf("Info", "Userinfo"),
        args = listOf(
            Argument(type = "MemberMentions", example = "@exampleMember", necessary = true),
        ),
        description = "Shows the commands you can use",
        accessRank = DiscordRank.TEAM
    )

    override fun action(cmd: CommandContainer) {
        val message = IMessage(author = "Userinfo", subTitle = "Stored Info in DB")
        if (cmd.mentioned.size <= 0) {
            limitedAccess(cmd = cmd, reason = LimitationReason.ARGUMENTS_WRONG)
            return
        }
        for (m in cmd.mentioned) {
            val nationMember = NationMemberImpl(m)
            message.addLine(text = "__${m.effectiveName}__")
                .addField(name = "Database", value = "no DB connected", inline = false)
                .addField(name = "Rank", value = "${nationMember.rank}", inline = false)
                .addField(name = "Game", value = "${nationMember.game}", inline = false)
                .blankLine()
        // TODO: send info about user instead of "No Database" as soon as db is
        }
        cmd.channel.sendMessage(message.build()).queue()
    }
}