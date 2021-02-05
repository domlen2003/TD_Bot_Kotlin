package commands.handling

import security.DiscordRank

data class CommandInfo(
    val name: String,
    val invokes: List<String>,
    val accessRank: DiscordRank = DiscordRank.THE_NATION,
    val description: String = "",
    val args: List<Argument>,
    val argsNecessary: Boolean = true
)

data class Argument(
    val type: String,
    val example: String
)