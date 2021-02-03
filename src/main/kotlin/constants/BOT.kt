package constants

import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

object BOT {
    const val PREFIX = "/"
    val STATUS = OnlineStatus.ONLINE
    val ACTIVITY = Activity.watching("TD-Nation")
    val GATEWAY_INTENTS = GatewayIntent.ALL_INTENTS
}