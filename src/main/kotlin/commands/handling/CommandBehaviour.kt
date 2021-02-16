package commands.handling

import constants.MESSAGES
import security.DiscordRank
import utils.Message
import java.util.concurrent.TimeUnit

abstract class CommandBehaviour:ICommand {
    override fun secure(cmd: CommandHandler.CommandContainer): Boolean {
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

    fun limitedAccess(cmd: CommandHandler.CommandContainer, reason: LimitationReason) {
        val msg: Message
        when (reason) {
            LimitationReason.ARGUMENTS_MISSING -> {
                msg =
                    Message(author = "", subTitle = "Missing Arguments (Non Fatal)").setColor(
                        MESSAGES.MESSAGE_BUILDER_WARNING_COLOR
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
                msg.setIcon(MESSAGES.MESSAGE_BUILDER_WARNING_ICON)
            }
            LimitationReason.ARGUMENTS_WRONG -> {
                msg = Message(author = "", subTitle = "Wrong Arguments").setColor(MESSAGES.MESSAGE_BUILDER_WARNING_COLOR)
                    .addLine("__**Expected Type: **__")
                for (i in info.args.indices) msg.addField(
                    name = "Field ${i + 1}",
                    value = "`${info.args[i].type}`",
                    inline = true
                )
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
                msg.setIcon(MESSAGES.MESSAGE_BUILDER_WARNING_ICON)
            }
            LimitationReason.RANK_WRONG -> {
                msg = Message(author = "", subTitle = "Restricted Access").setColor(MESSAGES.MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected Rank: **__")
                    .addField(name = "Rank", value = "`${info.accessRank.name}`", inline = true)
                    .blankLine()
                    .addLine("If necessary, ask any TEAM or OP Member to execute the command for you.")
                    .setIcon(MESSAGES.MESSAGE_BUILDER_ERROR_ICON)
            }
            LimitationReason.ARGUMENTS_MISSING_FATAL -> {
                msg = Message(author = "", subTitle = "Missing Arguments").setColor(MESSAGES.MESSAGE_BUILDER_ERROR_COLOR)
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
                msg.setIcon(MESSAGES.MESSAGE_BUILDER_ERROR_ICON)
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