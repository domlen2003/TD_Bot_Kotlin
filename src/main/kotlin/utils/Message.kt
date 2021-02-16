package utils

import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_ICON
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.time.Instant

class Message(private val author: String, private val subTitle: String) {

    private var body: String = "\u200C"
    private var color = MESSAGE_BUILDER_DEFAULT_COLOR
    private var iconUrl: String? = MESSAGE_BUILDER_DEFAULT_ICON
    private var imageUrl: String? = null
    private var titleUrl: String? = null

    fun addLine(text: String): Message {
        body = "$body\n$text"
        return this
    }

    fun blankLine(): Message {
        body = "$body\n"
        return this
    }

    fun addField(name: String?, value: String?, inline: Boolean): Message {
        body = if (inline)
            "${body}\n**${name}: **${value}"
        else
            "${body}\n**${name}: **\n${value}"
        return this
    }

    fun setIcon(url: String?): Message {
        this.iconUrl = url
        return this
    }

    fun setImage(url: String?): Message {
        this.imageUrl = url
        return this
    }

    fun setTitleUrl(url: String?): Message {
        titleUrl = url
        return this
    }


    fun setColor(color: Color): Message {
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
            .setImage(imageUrl)
            .setFooter("TDBot \u00A9 by TDDominik")
            .setTimestamp(Instant.now())
        return builder.build()
    }
}
