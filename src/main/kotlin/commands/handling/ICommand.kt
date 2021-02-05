package commands.handling

import commands.handling.CommandHandler.CommandContainer
import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import security.DiscordRank
import utils.IMessage
import java.util.concurrent.TimeUnit

interface ICommand {
    val info: CommandInfo

    fun secure(cmd: CommandContainer): Boolean {
        var secure = true
        if (info.args.size > cmd.args.size) {
            if(argsProblemBreak(cmd)) secure = false
        }
        if(!DiscordRank.findRank(cmd.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)) secure = false
        return secure
    }

    fun action(cmd: CommandContainer)

    fun argsProblemBreak(cmd: CommandContainer): Boolean {
        val msg = IMessage(author = "", subTitle = "Limited Access").setColor(MESSAGE_BUILDER_ERROR_COLOR)
            .addLine("__**Expected: **__")
        for (arg in info.args) msg.addField(name = arg.type, value = "`${arg.example}`", inline = true)

        msg.addLine(text = "__**Given: **__")
        for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
        if (cmd.args.isEmpty()) msg.addLine(text = "none")

        cmd.channel.sendMessage(msg.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS)
        cmd.message.delete().queueAfter(10, TimeUnit.SECONDS)
        return info.argsNecessary
    }
}