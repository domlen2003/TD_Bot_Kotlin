package de.th3ph4nt0m.kotlinbot.core


import net.dv8tion.jda.api.JDA

interface Bot {
    /**
     * The [JDA] instance
     */
    val jda: JDA
}