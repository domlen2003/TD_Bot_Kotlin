package utils

import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_COLOR
import constants.MESSAGES.MESSAGE_BUILDER_DEFAULT_ICON
import core.Bot
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.util.*

class IMessage(private val bot: Bot, private val title: String, private val subTitle: String) {

    private val fields: MutableList<MessageEmbed.Field> = ArrayList()
    private var body: String? = null
    private var color = MESSAGE_BUILDER_DEFAULT_COLOR
    private var iconUrl: String? = MESSAGE_BUILDER_DEFAULT_ICON
    private var titleUrl: String? = null
    private var authorUrl: String? = null
    private var authorIconUrl: String? = bot.jda.guilds[0].iconUrl


    fun setTitleUrl(pUrl: String?): IMessage {
        titleUrl = pUrl
        return this
    }

    fun addLine(pText: String): IMessage {
        body =
            if (body != null) """$body$pText""".trimIndent()
            else pText
        return this
    }

    fun addField(pName: String?, pValue: String?, pInline: Boolean): IMessage {
        val f = MessageEmbed.Field(pName, pValue, pInline)
        fields.add(f)
        return this
    }

    fun blankLine(): IMessage {
        body =
            if (body != null) """$body""".trimIndent()
            else "\n"
        return this
    }

    fun setColor(pColor: Color): IMessage {
        color = pColor
        return this
    }

    fun setIcon(pIconUrl: String?): IMessage {
        iconUrl = pIconUrl
        return this
    }

    fun setAuthorUrl(pAuthorUrl: String?): IMessage {
        authorUrl = pAuthorUrl
        return this
    }

    fun setAuthorIconUrl(pAuthorIconUrl: String?): IMessage {
        authorIconUrl = pAuthorIconUrl
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
