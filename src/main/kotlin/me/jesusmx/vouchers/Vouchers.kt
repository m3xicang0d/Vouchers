package me.jesusmx.vouchers

import me.jesusmx.vouchers.command.VoucherCommand
import me.jesusmx.vouchers.handlers.InventoryHandler
import me.jesusmx.vouchers.handlers.VoucherHandler
import me.jesusmx.vouchers.setup.Verify
import me.jesusmx.vouchers.utils.CC.translateS
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Vouchers : JavaPlugin() {
    override fun onEnable() {
        instance = this
        val config = File(dataFolder, "config.yml")
        if (!config.exists()) {
            getConfig().options().copyDefaults(true)
            saveConfig()
        }
        getCommand("vouchers").executor = VoucherCommand()
        getCommand("vouchers").permission = "vouchers.command"
        getCommand("vouchers").permissionMessage = translateS("You no have permissions to use this command!")
        server.pluginManager.registerEvents(InventoryHandler(), this)
        server.pluginManager.registerEvents(VoucherHandler(), this)
        Verify.setup()
    }

    override fun onDisable() {
    }

    companion object {
        @JvmStatic
        var instance: Vouchers? = null
            private set
    }
}