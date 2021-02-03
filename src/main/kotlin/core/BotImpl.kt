package de.th3ph4nt0m.kotlinbot.core


import de.th3ph4nt0m.kotlinbot.constants.BOT.ACTIVITY
import de.th3ph4nt0m.kotlinbot.constants.BOT.GATEWAY_INTENTS
import de.th3ph4nt0m.kotlinbot.constants.BOT.STATUS

import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

internal class BotImpl(
    token: String,
    env: Dotenv
) : Bot {

    override val jda: JDA = JDABuilder.create(
        token,
        GatewayIntent.getIntents(GATEWAY_INTENTS)
    )
        .setActivity(ACTIVITY)
        .setStatus(STATUS)
        .build()

}