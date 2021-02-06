package utils

import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_ICON
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.time.Instant
import java.time.temporal.TemporalAccessor
import java.util.*

class IMessage(private val author: String, private val subTitle: String) {

    private var body: String = "\u200C"
    private var color = MESSAGE_BUILDER_DEFAULT_COLOR
    private var iconUrl: String? = MESSAGE_BUILDER_DEFAULT_ICON
    private var titleUrl: String? = null

    fun addLine(text: String): IMessage {
        body = "$body\n$text\n"
        return this
    }

    fun blankLine(): IMessage {
        body = "$body\n"
        return this
    }

    fun addField(name: String?, value: String?, inline: Boolean): IMessage {
        body = if (inline)
            "${body}\n**${name}: **${value}\n"
        else
            "${body}\n**${name}: **\n${value}\n"
        return this
    }

    fun setIcon(url: String?): IMessage {
        this.iconUrl = url
        return this
    }

    fun setTitleUrl(url: String?): IMessage {
        titleUrl = url
        return this
    }


    fun setColor(color: Color): IMessage {
        this.color = color
        return this
    }

    fun build(): MessageEmbed {
        val builder = EmbedBuilder()
        builder.setTitle(subTitle, titleUrl)
            .setColor(color)
            .setDescription(body)
            .setAuthor(author, null, null)
            .setThumbnail(iconUrl)
            .setFooter("\u00A9 TDBot by TDDominik")
            .setTimestamp(Instant.now())
        return builder.build()
    }
}
