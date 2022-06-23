package me.jesusmx.vouchers.command

import me.jesusmx.vouchers.utils.CC.translate
import me.jesusmx.vouchers.utils.CC.translateS
import me.jesusmx.vouchers.Vouchers.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

class VoucherCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        return if (args.size == 3) {
            val player = Bukkit.getPlayerExact(args[2])
            if (player == null) {
                sender.sendMessage(translateS("&cThe player " + args[2] + " not is online!"))
                return false
            }
            val config = instance!!.config
            val voucher = config.getString("VOUCHERS." + args[1])
            if (voucher == null) {
                sender.sendMessage(translateS("&cThe voucher " + args[1] + " not exist!"))
                return false
            }
            val paper = config.getBoolean("VOUCHERS." + args[1] + ".PAPER")
            if (paper) {
                val paperItem = ItemStack(Material.PAPER)
                val meta = paperItem.itemMeta
                meta.displayName = translateS(config.getString("VOUCHERS." + args[1] + ".ITEM.NAME"))
                val lore = config.getStringList("VOUCHERS." + args[1] + ".ITEM.LORE")
                meta.lore = translate(lore)
                paperItem.itemMeta = meta
                player.inventory.addItem(paperItem)
            }
            val book = config.getBoolean("VOUCHERS." + args[1] + ".BOOK")
            if (book) {
                val bookItem = ItemStack(Material.BOOK)
                val bookMeta = bookItem.itemMeta
                bookMeta.displayName = translateS(config.getString("VOUCHERS." + args[1] + ".ITEM.NAME"))
                val bookLore = config.getStringList("VOUCHERS." + args[1] + ".ITEM.LORE")
                bookMeta.lore = translate(bookLore)
                bookItem.itemMeta = bookMeta
                player.inventory.addItem(bookItem)
            }
            sender.sendMessage(translateS("&aSuccesfully gived!"))
            true
        } else if (args.size == 1) {
            if (args[0].equals("list", ignoreCase = true)) {
                val config = instance!!.config
                sender.sendMessage(translateS("&aVouchers:"))
                for (s in config.getConfigurationSection("VOUCHERS").getKeys(false)) {
                    sender.sendMessage(translateS(" &7- &e$s"))
                }
                return true
            }
            false
        } else if (args.size == 4) {
            if (!isInteger(args[3])) {
                sender.sendMessage(translateS("&c" + args[3] + " not is a number!"))
                return false
            }
            val g = Integer.valueOf(args[3])
            val player = Bukkit.getPlayerExact(args[2])
            if (player == null) {
                sender.sendMessage(translateS("&cThe player " + args[2] + " not is online!"))
                return false
            }
            val config = instance!!.config
            val voucher = config.getString("VOUCHERS." + args[1])
            if (voucher == null) {
                sender.sendMessage(translateS("&cThe voucher " + args[1] + " not exist!"))
                return false
            }
            val paper = config.getBoolean("VOUCHERS." + args[1] + ".PAPER")
            if (paper) {
                val paperItem = ItemStack(Material.PAPER)
                val meta = paperItem.itemMeta
                meta.displayName = translateS(config.getString("VOUCHERS." + args[1] + ".ITEM.NAME"))
                val lore = config.getStringList("VOUCHERS." + args[1] + ".ITEM.LORE")
                meta.lore = translate(lore)
                paperItem.itemMeta = meta
                for (i in 0 until g) {
                    player.inventory.addItem(paperItem)
                }
            }
            val book = config.getBoolean("VOUCHERS." + args[1] + ".BOOK")
            if (book) {
                val bookItem = ItemStack(Material.BOOK)
                val bookMeta = bookItem.itemMeta
                bookMeta.displayName = translateS(config.getString("VOUCHERS." + args[1] + ".ITEM.NAME"))
                val bookLore = config.getStringList("VOUCHERS." + args[1] + ".ITEM.LORE")
                bookMeta.lore = translate(bookLore)
                bookItem.itemMeta = bookMeta
                for (i in 0 until g) {
                    player.inventory.addItem(bookItem)
                }
            }
            sender.sendMessage(translateS("&aSuccesfully gived!"))
            true
        } else {
            sender.sendMessage(translateS("&cCorrect usage: /$label give <voucher> <player>"))
            false
        }
    }

    fun isInteger(input: String): Boolean {
        return try {
            input.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }
}