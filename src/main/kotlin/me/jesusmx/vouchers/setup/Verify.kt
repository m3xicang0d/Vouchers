package me.jesusmx.vouchers.setup

import me.jesusmx.vouchers.utils.CC.translate
import me.jesusmx.vouchers.utils.CC.translateS
import me.jesusmx.vouchers.Vouchers.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

object Verify {

    @JvmStatic
    var players: MutableList<Player>? = null
        private set
    private var trueInventory: List<Player>? = null
    fun setup() {
        players = ArrayList()
        trueInventory = ArrayList()
    }

    @JvmStatic
    fun setupInventory(name: String): Inventory {
        val inventory = Bukkit.createInventory(null, 27, translateS(instance!!.config.getString("VOUCHERS.$name.CONFIRMATION_GUI_NAME")))
        for (i in 0..8) {
            inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE))
        }
        for (i in 9..11) {
            inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE))
        }
        val yes = ItemStack(Material.EMERALD_BLOCK)
        val yesMeta = yes.itemMeta
        yesMeta.displayName = translateS("&aYES")
        val lore: MutableList<String?> = ArrayList()
        lore.add("&7- &aClick to accept")
        yesMeta.lore = translate(lore)
        yes.itemMeta = yesMeta
        inventory.setItem(12, yes)
        inventory.setItem(13, ItemStack(Material.STAINED_GLASS_PANE))
        val no = ItemStack(Material.REDSTONE_BLOCK)
        val noMeta = no.itemMeta
        noMeta.displayName = translateS("&cNO")
        val loreNo: MutableList<String?> = ArrayList()
        loreNo.add("&7- &cClick to decline")
        noMeta.lore = translate(loreNo)
        no.itemMeta = noMeta
        inventory.setItem(14, no)
        for (i in 15..17) {
            inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE))
        }
        for (i in 18..26) {
            inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE))
        }
        return inventory
    }

    @JvmStatic
    fun haveVoucher(player: Player): Boolean {
        val itemInHand = player.itemInHand
        for (s in instance!!.config.getConfigurationSection("VOUCHERS").getKeys(false)) {
            val paperStack = ItemStack(Material.PAPER)
            val paperMeta = paperStack.itemMeta
            paperMeta.displayName = translateS(instance!!.config.getString("VOUCHERS.$s.ITEM.NAME"))
            paperMeta.lore = translate(instance!!.config.getStringList("VOUCHERS.$s.ITEM.LORE"))
            paperStack.itemMeta = paperMeta
            val bookStack = ItemStack(Material.BOOK)
            val bookMeta = bookStack.itemMeta
            bookMeta.displayName = translateS(instance!!.config.getString("VOUCHERS.$s.ITEM.NAME"))
            bookMeta.lore = translate(instance!!.config.getStringList("VOUCHERS.$s.ITEM.LORE"))
            bookStack.itemMeta = bookMeta
            if (instance!!.config.getBoolean("VOUCHERS.$s.PAPER") || instance!!.config.getBoolean("VOUCHERS.$s.BOOK")) {
                if (itemInHand.isSimilar(paperStack) || itemInHand.isSimilar(bookStack)) {
                    return if (player.hasPermission(instance!!.config.getString("VOUCHERS.$s.PERMISSION_TO_USE"))) {
                        if (instance!!.config.getBoolean("VOUCHERS.$s.NEED_CONFIRMATION")) {
                            if (!players!!.contains(player)) {
                                players!!.add(player)
                                player.openInventory(setupInventory(s))
                            } else {
                                throw Error("ERROR, BAD CAUSE!, Please report to: FxMxGRAGFX#0001 (Discord)")
                            }
                        }
                        true
                    } else {
                        player.sendMessage(translateS("&cYou no have permissions to redeem this voucher!"))
                        false
                    }
                }
            } else {
                throw Error("ERROR, BAD CONFIG.YML: <$s>, Please report to: FxMxGRAGFX#0001 (Discord)")
            }
        }
        return false
    }

    @JvmStatic
    fun getVoucher(player: Player): String {
        val itemInHand = player.itemInHand
        for (s in instance!!.config.getConfigurationSection("VOUCHERS").getKeys(false)) {
            val paperStack = ItemStack(Material.PAPER)
            val paperMeta = paperStack.itemMeta
            paperMeta.displayName = translateS(instance!!.config.getString("VOUCHERS.$s.ITEM.NAME"))
            paperMeta.lore = translate(instance!!.config.getStringList("VOUCHERS.$s.ITEM.LORE"))
            paperStack.itemMeta = paperMeta
            val bookStack = ItemStack(Material.BOOK)
            val bookMeta = bookStack.itemMeta
            bookMeta.displayName = translateS(instance!!.config.getString("VOUCHERS.$s.ITEM.NAME"))
            bookMeta.lore = translate(instance!!.config.getStringList("VOUCHERS.$s.ITEM.LORE"))
            bookStack.itemMeta = bookMeta
            if (instance!!.config.getBoolean("VOUCHERS.$s.PAPER") || instance!!.config.getBoolean("VOUCHERS.$s.BOOK")) {
                if (itemInHand.isSimilar(paperStack) || itemInHand.isSimilar(bookStack)) {
                    return s
                }
            } else {
                throw Error("ERROR, BAD CONFIG.YML: <$s>")
            }
        }
        return ""
    }
}