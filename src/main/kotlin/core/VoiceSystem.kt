package core

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import java.util.*

class VoiceSystem {
    var voiceChannels = ArrayList<VoiceChannel>()

    fun createVoiceChannel(game: String, guild: Guild, member: Member, joined: VoiceChannel) {
        val temp = guild
            .createVoiceChannel("» $game")
            .setParent(joined.parent)
            .setPosition(voiceChannels.size + 1)
        val channel = temp.complete()
        voiceChannels.add(channel)
        member.guild.moveVoiceMember(member, channel).queue()
    }

    fun createCompChannel(game: String, guild: Guild, member: Member, joined: VoiceChannel) {
        var limit = 0

        val find = CompetitiveGames.values().find { it.displayName == game }
        if(find!=null) limit=find.userCount

        val channel = guild
            .createVoiceChannel("» Comp × $game")
            .setParent(joined.parent)
            .setPosition(voiceChannels.size + 1)
            .setUserlimit(limit)
            .complete()
        voiceChannels.add(channel)
        member.guild.moveVoiceMember(member, channel).queue()
    }
}

enum class CompetitiveGames(val displayName: String, val userCount: Int) {
    OVERWATCH(displayName = "Overwatch", userCount = 6),
    VALORANT(displayName = "Valorant", userCount = 5),
    RAINBOW_SIX(displayName = "Rainbow Six Siege", userCount = 5),
    CSGO(displayName = "Counter-Strike: Global Offensive", userCount = 5)
}