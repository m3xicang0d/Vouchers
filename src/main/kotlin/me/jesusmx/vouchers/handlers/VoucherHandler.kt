package me.jesusmx.vouchers.handlers

import me.jesusmx.vouchers.Vouchers.Companion.instance
import me.jesusmx.vouchers.setup.Verify.getVoucher
import me.jesusmx.vouchers.setup.Verify.haveVoucher
import me.jesusmx.vouchers.utils.CC
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class VoucherHandler : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (haveVoucher(event.player)) {
            val player = event.player
            val voucher = getVoucher(player)
            if (!instance!!.config.getBoolean("VOUCHERS.$voucher.NEED_CONFIRMATION")) {
                val action = event.action
                val rAir = instance!!.config.getBoolean("VOUCHERS.$voucher.RIGHT_CLICK_AIR")
                val lAir = instance!!.config.getBoolean("VOUCHERS.$voucher.LEFT_CLICK_AIR")
                val rBlock = instance!!.config.getBoolean("VOUCHERS.$voucher.RIGHT_CLICK_BLOCK")
                val lBlock = instance!!.config.getBoolean("VOUCHERS.$voucher.LEFT_CLICK_BLOCK")
                if (rAir) {
                    player.itemInHand.amount = player.itemInHand.amount - 1
                    if(player.itemInHand.amount == 1) {
                        player.inventory.remove(player.itemInHand)
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                    if (instance!!.config.getBoolean("VOUCHERS.$voucher.COMMAND_LOG")) {
                        Bukkit.getConsoleSender().sendMessage("Voucher redeemed by " + player.name + " : " + instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                    }
                    player.sendMessage(CC.translateS("&aVoucher succesfully redeemed!"))
                } else if (lAir) {
                    if (action == Action.LEFT_CLICK_AIR) {
                        player.itemInHand.amount = player.itemInHand.amount - 1
                        if(player.itemInHand.amount == 1) {
                            player.inventory.remove(player.itemInHand)
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        if (instance!!.config.getBoolean("VOUCHERS.$voucher.COMMAND_LOG")) {
                            Bukkit.getConsoleSender().sendMessage("Voucher redeemed by " + player.name + " : " + instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        }
                        player.sendMessage(CC.translateS("&aVoucher succesfully redeemed!"))
                    }
                } else if (rBlock) {
                    if (action == Action.RIGHT_CLICK_BLOCK) {
                        player.itemInHand.amount = player.itemInHand.amount - 1
                        if(player.itemInHand.amount == 1) {
                            player.inventory.remove(player.itemInHand)
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        if (instance!!.config.getBoolean("VOUCHERS.$voucher.COMMAND_LOG")) {
                            Bukkit.getConsoleSender().sendMessage("Voucher redeemed by " + player.name + " : " + instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        }
                        player.sendMessage(CC.translateS("&aVoucher succesfully redeemed!"))
                    }
                } else if (lBlock) {
                    if (action == Action.LEFT_CLICK_BLOCK) {
                        player.itemInHand.amount = player.itemInHand.amount - 1
                        if(player.itemInHand.amount == 1) {
                            player.inventory.remove(player.itemInHand)
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        if (instance!!.config.getBoolean("VOUCHERS.$voucher.COMMAND_LOG")) {
                            Bukkit.getConsoleSender().sendMessage("Voucher redeemed by " + player.name + " : " + instance!!.config.getString("VOUCHERS.$voucher.COMMAND").replace("%player%".toRegex(), player.name))
                        }
                        player.sendMessage(CC.translateS("&aVoucher succesfully redeemed!"))
                    }
                } else {
                    throw Error("ERROR, BAD CONFIG.YML: <$voucher> Please report to: FxMxGRAGFX#0001 (Discord)")
                }
                event.isCancelled = true
            }
        }
    }
}