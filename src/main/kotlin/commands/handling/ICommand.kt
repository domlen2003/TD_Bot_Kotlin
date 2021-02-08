package commands.handling

import commands.handling.CommandHandler.CommandContainer
import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_ERROR_ICON
import constants.MESSAGES.MESSAGE_BUILDER_WARNING_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_WARNING_ICON
import security.DiscordRank
import utils.IMessage
import java.util.concurrent.TimeUnit

interface ICommand {
    val info: CommandInfo

    fun secure(cmd: CommandContainer): Boolean {
        info.args.dropWhile { argument -> !argument.necessary }.size
        if (info.args.dropWhile { argument -> !argument.necessary }.size > cmd.args.size) {
            limitedAccess(cmd = cmd, reason = LimitationReason.ARGUMENTS_MISSING_FATAL)
            return false
        } else if (!DiscordRank.findRank(cmd.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)) {
            limitedAccess(cmd = cmd, reason = LimitationReason.RANK_WRONG)
            return false
        } else if (info.args.size > cmd.args.size) {
            limitedAccess(cmd = cmd, reason = LimitationReason.ARGUMENTS_MISSING)
        }
        return true
    }

    fun action(cmd: CommandContainer)

    fun limitedAccess(cmd: CommandContainer, reason: LimitationReason) {
        val msg: IMessage
        when (reason) {
            LimitationReason.ARGUMENTS_MISSING -> {
                msg =
                    IMessage(author = "", subTitle = "Missing Arguments (Non Fatal)").setColor(
                        MESSAGE_BUILDER_WARNING_COLOR
                    )
                        .addLine("__**Expected: **__")
                for (arg in info.args) msg.addField(
                    name = arg.type, value = "`${arg.example}` ${
                        if (arg.necessary) {
                            " required"
                        } else {
                            " not required"
                        }
                    }", inline = true
                )
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
                if (cmd.args.isEmpty()) msg.addLine(text = "none")
                msg.setIcon(MESSAGE_BUILDER_WARNING_ICON)
            }
            LimitationReason.ARGUMENTS_WRONG -> {
                msg = IMessage(author = "", subTitle = "Wrong Arguments").setColor(MESSAGE_BUILDER_WARNING_COLOR)
                    .addLine("__**Expected Type: **__")
                for (i in info.args.indices) msg.addField(
                    name = "Field ${i + 1}",
                    value = "`${info.args[i].type}`",
                    inline = true
                )
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
                msg.setIcon(MESSAGE_BUILDER_WARNING_ICON)
            }
            LimitationReason.RANK_WRONG -> {
                msg = IMessage(author = "", subTitle = "Restricted Access").setColor(MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected Rank: **__")
                    .addField(name = "Rank", value = "`${info.accessRank.name}`", inline = true)
                    .blankLine()
                    .addLine("If necessary, ask any TEAM or OP Member to execute the command for you.")
                    .setIcon(MESSAGE_BUILDER_ERROR_ICON)
            }
            LimitationReason.ARGUMENTS_MISSING_FATAL -> {
                msg = IMessage(author = "", subTitle = "Missing Arguments").setColor(MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected: **__")
                for (arg in info.args) msg.addField(
                    name = arg.type, value = "`${arg.example}` ${
                        if (arg.necessary) {
                            " required"
                        } else {
                            " not required"
                        }
                    }", inline = true
                )
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
                if (cmd.args.isEmpty()) msg.addLine(text = "none")
                msg.setIcon(MESSAGE_BUILDER_ERROR_ICON)
            }
        }

        cmd.channel.sendMessage(msg.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS)
    }
}

enum class LimitationReason {
    ARGUMENTS_MISSING,
    ARGUMENTS_MISSING_FATAL,
    ARGUMENTS_WRONG,
    RANK_WRONG
}