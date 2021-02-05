package commands.handling

import commands.handling.CommandHandler.CommandContainer
import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import security.DiscordRank
import utils.IMessage
import java.util.concurrent.TimeUnit

interface ICommand {
    val info: CommandInfo

    fun secure(cmd: CommandContainer): Boolean {
        if (info.args.size > cmd.args.size) return argsMissingBreak(cmd)
        return DiscordRank.findRank(cmd.event!!.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)
    }

    fun action(cmd: CommandContainer)

    fun argsMissingBreak(cmd: CommandContainer): Boolean {
        val msg = IMessage(title = "", subTitle = "Arguments Missing").setColor(MESSAGE_BUILDER_ERROR_COLOR)
            .addLine("__**Expected: **__")
        for (arg in info.args) msg.addField(name = arg.type, value = "`${arg.example}`", inline = true)

        msg.addLine(text = "__**Given: **__")
        for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
        if (cmd.args.isEmpty()) msg.addLine(text = "none")

        cmd.event!!.textChannel.sendMessage(msg.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS)
        cmd.event.message.delete().queue()
        return false
    }
}