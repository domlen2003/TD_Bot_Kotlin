package commands.handling

import commands.cmds.ClearCommand
import commands.cmds.HelpCommand
import commands.cmds.TestCommand
import commands.cmds.UserinfoCommand
import constants.BOT.PREFIX
import core.Bot
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

class CommandHandler(bot: Bot) {

    private val commands: LinkedList<ICommand> = LinkedList<ICommand>()

    init {
        addCommand(TestCommand())
        addCommand(HelpCommand(bot))
        addCommand(UserinfoCommand())
        addCommand(ClearCommand(bot))
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
            if (command.info.invokes.stream().anyMatch { it.toString().equals(cmd.invoke, ignoreCase = true) }) {
                if (command.secure(cmd)) command.action(cmd)
            }
        }
    }

    fun listCommandInfos(): LinkedList<CommandInfo> {
        val list = LinkedList<CommandInfo>()
        commands.forEach { command -> list.add(command.info) }
        return list
    }

    private fun parse(raw: String, event: MessageReceivedEvent): CommandContainer {
        val beheaded = raw.replaceFirst(PREFIX.toRegex(), "")
        val splitBeheaded = LinkedList(beheaded.split(" ").toList().dropWhile { it == " " || it == "" })
        val invoke = splitBeheaded[0]
        val args = LinkedList(splitBeheaded.toList().subList(1, splitBeheaded.size))

        return CommandContainer(invoke, args, event.textChannel, event.member, event.message.mentionedMembers)
    }

    data class CommandContainer(
        val invoke: String,
        val args: List<String?>,
        val channel: TextChannel,
        val member: Member?,
        val mentioned: MutableList<Member>
    )
}
