package security

enum class DiscordRank(private val ID: Long) {
    OP(645019781053022228L),
    BOT(695789952839057490L),
    TEAM(747185518827012156L),
    VIP(721076804210786366L),
    THE_NATION(721076810120429610L),
    UNVERIFIED(432905467753136128L);

    fun isAtLeast(rank: DiscordRank): Boolean {
        return ordinal <= rank.ordinal
    }

    companion object {
        /**
         * find a rank by name
         *
         * @param name the rank to search for
         * @return rank || null
         */
        fun findRank(name: String?): DiscordRank? {
            for (discordRank in values()) {
                if (discordRank.name.equals(name, ignoreCase = true)) return discordRank
            }
            return null
        }

        /**
         * find a rank by id
         *
         * @param id the rank to search for
         * @return rank || null
         */
        fun findRank(id: Long): DiscordRank? {
            for (discordRank in values()) {
                if (discordRank.ID == id) return discordRank
            }
            return null
        }
    }
}