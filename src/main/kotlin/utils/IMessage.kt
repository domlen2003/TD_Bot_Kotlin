package utils

import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_ICON
import core.Bot
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.util.*

class IMessage(bot: Bot?, private val title: String, private val subTitle: String) {

    private val fields: MutableList<MessageEmbed.Field> = ArrayList()
    private var body: String? = null
    private var color = MESSAGE_BUILDER_DEFAULT_COLOR
    private var iconUrl: String? = MESSAGE_BUILDER_DEFAULT_ICON
    private var titleUrl: String? = null
    private var authorUrl: String? =
        bot?.jda?.guilds?.getOrNull(0)?.retrieveInvites()?.complete()?.getOrNull(0)?.url ?:
        bot?.jda?.guilds?.getOrNull(0)?.channels?.getOrNull(1)?.createInvite()?.complete()?.url
    private var authorIconUrl: String? = bot?.jda?.guilds?.get(0)?.iconUrl


    fun setTitleUrl(url: String?): IMessage {
        titleUrl = url
        return this
    }

    fun addLine(text: String): IMessage {
        body =
            if (body != null) """$body$text""".trimIndent()
            else text
        return this
    }

    fun addField(name: String?, value: String?, inline: Boolean): IMessage {
        val f = MessageEmbed.Field(name, value, inline)
        fields.add(f)
        return this
    }

    fun blankLine(): IMessage {
        body =
            if (body != null) """$body""".trimIndent()
            else "\n"
        return this
    }

    fun setColor(color: Color): IMessage {
        this.color = color
        return this
    }

    fun setIcon(url: String?): IMessage {
        this.iconUrl = url
        return this
    }

    fun setAuthorUrl(url: String?): IMessage {
        this.authorUrl = url
        return this
    }

    fun setAuthorIconUrl(url: String?): IMessage {
        this.authorIconUrl = url
        return this
    }

    fun build(): MessageEmbed {
        val builder = EmbedBuilder()
        builder.setTitle(subTitle, titleUrl)
            .setColor(color)
            .setDescription(body)
            .setAuthor(title, authorUrl, authorIconUrl)
            .setThumbnail(iconUrl)
        for (field in fields) {
            builder.addField(field)
        }
        return builder.build()
    }
}
