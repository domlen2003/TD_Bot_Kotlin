package security

enum class DiscordRank(private val ID: Long) {
    OP(773649381445599232L),
    BOT(773649384159182868L),
    TEAM(806517496702173196L),
    VIP(806517497725452338L),
    THE_NATION(806517498513457162L),
    UNVERIFIED(697220825132171275L);

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