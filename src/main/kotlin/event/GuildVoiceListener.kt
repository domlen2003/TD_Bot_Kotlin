package event

import constants.VOICE.COMP_CREATE_CHANNEL_ID
import constants.VOICE.CREATE_CHANNEL_ID
import core.Bot
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import security.DiscordRank
import utils.NationMemberImpl

class GuildVoiceListener(private val bot: Bot) : ListenerAdapter() {
    /** Listener for command handling  */
    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
        channelCreate(
            EventContainer(
                guild = event.guild,
                member = event.member,
                joined = event.channelJoined
            )
        )
    }

    override fun onGuildVoiceMove(event: GuildVoiceMoveEvent) {
        channelCreate(
            EventContainer(
                guild = event.guild,
                member = event.member,
                joined = event.channelJoined
            )
        )
        if (bot.voiceSystem.voiceChannels.contains(event.channelLeft)) {
            if (event.channelLeft.members.size <= 0) {
                event.channelLeft.delete().queue()
                bot.voiceSystem.voiceChannels.remove(event.channelLeft)
            }
        }
    }

    override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent) {

        // delete custom channel as soon as empty
        if (bot.voiceSystem.voiceChannels.contains(event.channelLeft)) {
            if (event.channelLeft.members.size <= 0) {
                event.channelLeft.delete().queue()
                bot.voiceSystem.voiceChannels.remove(event.channelLeft)
            }
        }
    }

    private fun channelCreate(event: EventContainer) {
        val nMember = NationMemberImpl(event.member)
        if (event.joined.id == CREATE_CHANNEL_ID) {
            if (nMember.rank!!.isAtLeast(DiscordRank.THE_NATION)) {
                if (nMember.game != null) {
                    bot.voiceSystem
                        .createVoiceChannel(
                            game = nMember.game!!,
                            guild = event.guild,
                            member = event.member,
                            joined = event.joined
                        )
                } else {
                    bot.voiceSystem
                        .createVoiceChannel(
                            game = "Talk",
                            guild = event.guild,
                            member = event.member,
                            joined = event.joined
                        )
                }
            } else {
                event.member.guild.kickVoiceMember(event.member).queue()
            }
        } else if (event.joined.id == COMP_CREATE_CHANNEL_ID) {
            if (nMember.rank!!.isAtLeast(DiscordRank.THE_NATION)) {
                if (nMember.game != null) {
                    bot.voiceSystem
                        .createCompChannel(
                            game = nMember.game!!,
                            guild = event.guild,
                            member = event.member,
                            joined = event.joined
                        )
                }
            } else {
                event.member.guild.kickVoiceMember(event.member).queue()
            }
        }
    }

    private data class EventContainer(
        val guild: Guild,
        val member: Member,
        val joined: VoiceChannel
    )
}