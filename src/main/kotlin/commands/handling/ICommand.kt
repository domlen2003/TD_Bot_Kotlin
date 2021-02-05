package commands.handling

import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank
import utils.IMessage
import java.util.concurrent.TimeUnit

interface ICommand {
    val info: CommandInfo

    fun secure(args: List<String?>, event: MessageReceivedEvent?): Boolean {
        if (info.args.size > args.size) return argsMissingBreak(args = args, event = event!!)
        return DiscordRank.findRank(event!!.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)
    }

    fun action(args: List<String?>, event: MessageReceivedEvent?)

    fun argsMissingBreak(args: List<String?>, event: MessageReceivedEvent): Boolean {
        val msg = IMessage(title = "", subTitle = "Arguments Missing").setColor(MESSAGE_BUILDER_ERROR_COLOR)
            .addLine("__**Expected: **__")
        for (arg in info.args) msg.addField(name = arg.type, value = "`${arg.example}`", inline = true)

        msg.addLine(text = "__**Given: **__")
        for (i in args.indices) msg.addField(name = "Field ${i + 1}", value = args[i], inline = true)
        if (args.isEmpty()) msg.addLine(text = "none")
        event.textChannel.sendMessage(msg.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS)
        event.message.delete().queue()
        return false
    }
}