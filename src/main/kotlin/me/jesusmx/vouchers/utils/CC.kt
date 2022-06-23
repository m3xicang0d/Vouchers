package me.jesusmx.vouchers.utils

import net.md_5.bungee.api.ChatColor
import java.util.*

object CC {

    @JvmStatic
    fun translateS(input: String?): String {
        return ChatColor.translateAlternateColorCodes('&', input)
    }

    @JvmStatic
    fun translate(input: List<String?>): List<String> {
        val toReturn: MutableList<String> = ArrayList()
        for (s in input) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', s))
        }
        return toReturn
    }
}