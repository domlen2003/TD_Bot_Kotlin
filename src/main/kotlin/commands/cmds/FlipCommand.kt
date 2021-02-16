package commands.cmds

import commands.handling.CommandBehaviour
import commands.handling.CommandHandler.CommandContainer
import commands.handling.CommandInfo
import constants.MESSAGES.FLIP_COIN_HEAD
import constants.MESSAGES.FLIP_COIN_TAIL
import utils.Message

class FlipCommand : CommandBehaviour() {
    override val info = CommandInfo(
        name = "FlipCoin",
        invokes = listOf("flip", "flipcoin", "toss", "cointoss"),
        description = "a test command"
    )

    override fun action(cmd: CommandContainer) {
        val msg = Message(author = "Coin Toss", subTitle = "Outcome of the Toss")
        msg.setImage(
            if (Math.random() < 0.5) {
                FLIP_COIN_HEAD
            } else {
                FLIP_COIN_TAIL
            }
        )
        cmd.channel.sendMessage(msg.build()).queue()
    }

}