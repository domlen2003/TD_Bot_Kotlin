package event

import core.Bot

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildVoiceListener(private val bot: Bot) : ListenerAdapter() {
    /** Listener for command handling  */
    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent){
        TODO("add channel create etc")
    }

    override fun onGuildVoiceMove(event: GuildVoiceMoveEvent) {
        TODO("add channel create etc")
    }

    override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent) {
        TODO("add channel create etc")
    }
}