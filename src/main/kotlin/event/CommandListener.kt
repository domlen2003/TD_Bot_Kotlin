package event

import constants.BOT.PREFIX
import core.Bot
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener(private val bot: Bot) : ListenerAdapter() {
    /** Listener for command handling  */
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.contentRaw.startsWith(PREFIX) && !event.member?.id.equals(bot.jda.selfUser.id)) {
            bot.commandHandler.handle(event)
        }
    }
}