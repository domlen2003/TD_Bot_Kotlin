package commands.handling

import commands.cmds.HelpCommand
import commands.cmds.TestCommand
import constants.BOT.PREFIX
import core.Bot
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*
import java.util.function.Predicate

class CommandHandler (bot: Bot){

    private val commands: LinkedList<ICommand> = LinkedList<ICommand>()

    init {
        addCommand(TestCommand(bot))
        addCommand(HelpCommand(bot))
    }

    fun handle(event: MessageReceivedEvent) {
        handleCommand(parse(event.message.contentRaw, event))
    }

    private fun addCommand(command: ICommand) {
        if (commands.contains(command)) return
        commands.add(command)
    }

    private fun handleCommand(cmd: CommandContainer) {
        for (command in commands) {
            if(command.info.invokes.stream().anyMatch{it.toString().equals(cmd.invoke, ignoreCase = true)}) {
                if (command.secure(cmd.args, cmd.event)) command.action(cmd.args, cmd.event)
            }
        }
    }

    fun listCommandinfos(): LinkedList<CommandInfo> {
        val list = LinkedList<CommandInfo>()
        commands.forEach { command ->list.add(command.info)  }
        return list
    }

    private data class CommandContainer(
            val raw: String,
            val beheaded: String,
            val splitBeheaded: Array<String>,
            val invoke: String,
            val args: Array<String?>,
            val event: MessageReceivedEvent
    )

    private fun parse(raw: String, event: MessageReceivedEvent): CommandContainer {
        val beheaded = raw.replaceFirst(PREFIX.toRegex(), "")
        val splitBeheaded = beheaded.split(" ").toTypedArray()
        val invoke = splitBeheaded[0]
        val split = ArrayList<String>()
        for (s in splitBeheaded) split.add(s)
        val args = arrayOfNulls<String>(split.size - 1)
        System.arraycopy(split.toArray(), 1, args, 0, args.size)

        return CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event)
    }
}