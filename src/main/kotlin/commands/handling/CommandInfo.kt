package commands.handling

import security.DiscordRank
import java.util.*


data class CommandInfo(
        val name: String,
        val invokes: List<String>,
        val accessRank: DiscordRank = DiscordRank.THE_NATION,
        val description: String = "",
        val args: List<String>
)