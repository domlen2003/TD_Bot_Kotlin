package utils

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Member
import security.DiscordRank

/**@param member Discord Member to create a NationMemberImpl from*/
class NationMemberImpl(private val member: Member): NationMember{
    override val game: String?
        get() {
            if (member.activities.size >= 1) {
                for (i in member.activities.indices) {
                    if (member.activities[i].type != Activity.ActivityType.CUSTOM_STATUS) {
                        return member.activities[i].name
                    }
                }
                return member.activities[0].name
            }
            return null
        }

    /** @return user's Rank*/
    override val rank: DiscordRank?
        get() = DiscordRank.findRank(member.roles[0].idLong)
}
