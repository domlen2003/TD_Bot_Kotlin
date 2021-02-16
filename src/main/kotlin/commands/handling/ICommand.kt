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

    fun secure(cmd: CommandContainer): Boolean

    fun action(cmd: CommandContainer)

}

