package core

import commands.handling.CommandHandler
import constants.BOT.ACTIVITY
import constants.BOT.GATEWAY_INTENTS
import constants.BOT.STATUS
import event.CommandListener
import event.GuildVoiceListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

internal class BotImpl(token: String) : Bot {

    override val jda: JDA = JDABuilder.create(
        token,
        GatewayIntent.getIntents(GATEWAY_INTENTS)
    )
        .addEventListeners(CommandListener(bot = this))
        .addEventListeners(GuildVoiceListener(bot = this))
        .setActivity(ACTIVITY)
        .setStatus(STATUS)
        .build()

    override val commandHandler = CommandHandler(bot = this)

    override val voiceSystem = VoiceSystem()

}
