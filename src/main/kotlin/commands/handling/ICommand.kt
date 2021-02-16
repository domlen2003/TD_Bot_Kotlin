package commands.handling

import commands.handling.CommandHandler.CommandContainer

interface ICommand {
    val info: CommandInfo

    fun secure(cmd: CommandContainer): Boolean

    fun action(cmd: CommandContainer)

}

