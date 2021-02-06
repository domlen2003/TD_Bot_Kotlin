package commands.handling

import commands.handling.CommandHandler.CommandContainer
import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import security.DiscordRank
import utils.IMessage
import java.util.concurrent.TimeUnit

interface ICommand {
    val info: CommandInfo

    fun secure(cmd: CommandContainer): Boolean {
        if (info.args.size > cmd.args.size) {
            limitedAccess(cmd = cmd, reason = LimitationReason.ARGUMENTS_MISSING)
            return false
        } else if (!DiscordRank.findRank(cmd.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)) {
            limitedAccess(cmd = cmd, reason = LimitationReason.RANK_WRONG)
            return false
        }
        return true
    }

    fun action(cmd: CommandContainer)

    fun limitedAccess(cmd: CommandContainer, reason: LimitationReason) {
        val msg : IMessage
        when(reason){
            LimitationReason.ARGUMENTS_MISSING ->{
                msg = IMessage(author = "", subTitle = "Missing Arguments").setColor(MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected: **__")
                for (arg in info.args) msg.addField(name = arg.type, value = "`${arg.example}`", inline = true)
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
                if (cmd.args.isEmpty()) msg.addLine(text = "none")
            }
            LimitationReason.ARGUMENTS_WRONG -> {
                msg = IMessage(author = "", subTitle = "Wrong Arguments").setColor(MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected Type: **__")
                for (i in info.args.indices) msg.addField(name = "Field ${i + 1}", value ="`${info.args[i].type}`" , inline = true)
                msg.addLine(text = "__**Given: **__")
                for (i in cmd.args.indices) msg.addField(name = "Field ${i + 1}", value = cmd.args[i], inline = true)
            }
            LimitationReason.RANK_WRONG -> {
                msg = IMessage(author = "", subTitle = "Restricted Access").setColor(MESSAGE_BUILDER_ERROR_COLOR)
                    .addLine("__**Expected Rank: **__")
                    .addField(name = "Rank", value = "`${info.accessRank.name}`", inline = true)
                    .blankLine()
                    .addLine("If necessary, ask any TEAM or OP Member to execute the command for you.")
            }
        }

        cmd.channel.sendMessage(msg.build()).complete().delete().queueAfter(8, TimeUnit.SECONDS)
    }
}

enum class LimitationReason {
    ARGUMENTS_MISSING,
    ARGUMENTS_WRONG,
    RANK_WRONG
}