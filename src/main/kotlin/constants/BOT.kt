package de.th3ph4nt0m.kotlinbot.constants

import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

object BOT {
    val STATUS = OnlineStatus.ONLINE
    val ACTIVITY = Activity.watching("TD-Nation")
    val GATEWAY_INTENTS = GatewayIntent.ALL_INTENTS
}