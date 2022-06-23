package me.jesusmx.vouchers.handlers

import me.jesusmx.vouchers.setup.Verify.players
import me.jesusmx.vouchers.setup.Verify.getVoucher
import me.jesusmx.vouchers.setup.Verify.setupInventory
import me.jesusmx.vouchers.utils.CC.translateS
import me.jesusmx.vouchers.utils.CC.translate
import me.jesusmx.vouchers.Vouchers.Companion.instance
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.entity.Player
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

class InventoryHandler : Listener {

    @EventHandler
    fun onPlayerClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) {
            return
        }
        val player = event.whoClicked as Player
        val itemClicked = event.currentItem
        if (players!!.contains(player)) {
            if (event.currentItem == null || event.slotType == null || event.currentItem.type == Material.AIR || event.currentItem.type == Material.STAINED_GLASS_PANE) {
                event.isCancelled = true
                return
            }
            event.isCancelled = true
            players!!.remove(player)
            val voucher = getVoucher(player)
            player.openInventory(setupInventory(voucher))
            val no = ItemStack(Material.REDSTONE_BLOCK)
            val noMeta = no.itemMeta
            noMeta.displayName = translateS("&cNO")
            val loreNo: MutableList<String?> = ArrayList()
            loreNo.add("&7- &cClick to decline")
            noMeta.lore = translate(loreNo)
            no.itemMeta = noMeta
            val yes = ItemStack(Material.EMERALD_BLOCK)
            val yesMeta = yes.itemMeta
            yesMeta.displayName = translateS("&aYES")
            val lore: MutableList<String?> = ArrayList()
            lore.add("&7- &aClick to accept")
            yesMeta.lore = translate(lore)
            yes.itemMeta = yesMeta
            if (itemClicked.isSimilar(yes)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                if (instance!!.config.getBoolean("VOUCHERS.$voucher.COMMAND_LOG")) {
                    Bukkit.getConsoleSender().sendMessage("Voucher redeemed by " + player.name + " : " + instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                }
                player.itemInHand.amount = player.itemInHand.amount - 1
                if(player.itemInHand.amount == 1) {
                    player.inventory.remove(player.itemInHand)
                }
                player.updateInventory()
                player.sendMessage(translateS("&aVoucher succesfully redeemed!"))
            } else if (itemClicked.isSimilar(no)) {
                player.sendMessage(translateS("&cVoucher redemption canceled!"))
            } else {
                return;
            }
            player.closeInventory()
        }
    }

    @EventHandler
    fun onPlayerClose(event: InventoryCloseEvent) {
        if (event.player !is Player) {
            return
        }
        val player = event.player as Player
        if (players!!.contains(player)) {
            player.sendMessage(translateS("&cVoucher redemption canceled!"))
            players!!.remove(player)
            player.closeInventory()
        }
    }
}