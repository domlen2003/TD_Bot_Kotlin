package utils

import security.DiscordRank

interface NationMember {
    /**
     * The [game] of the NationMember
     */
    val game: String?

    /**
     * The [DiscordRank] of the NationMember
     */
    val rank: DiscordRank?

}