package commands.handling

import constants.MESSAGES.MESSAGE_BUILDER_ERROR_COLOR
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import security.DiscordRank
import utils.IMessage

interface ICommand {
    val info: CommandInfo

    fun secure(args: List<String?>, event: MessageReceivedEvent?): Boolean {
        if(info.args.size>args.size) return argsMissingBreak(args = args, channel = event!!.textChannel)
        return  DiscordRank.findRank(event!!.member!!.roles[0].idLong)!!.isAtLeast(info.accessRank)
    }

    fun action(args: List<String?>, event: MessageReceivedEvent?)

    fun argsMissingBreak(args: List<String?>, channel: TextChannel): Boolean {
        val msg =  IMessage(bot = null, title = "", subTitle = "Arguments Missing").setColor(MESSAGE_BUILDER_ERROR_COLOR)
            .addLine("__**Expected: **__")
        for(arg in info.args) msg.addField(name = arg.type, value ="`${arg.example}`", inline = true)

        msg.addLine(text = "__**Given: **__")
        for(i in args.indices) msg.addField(name = "Field ${i+1}", value = args[i], inline = true)
        if(args.isEmpty()) msg.addLine(text = "none")

        channel.sendMessage(msg.build()).queue()
        return false
    }
}