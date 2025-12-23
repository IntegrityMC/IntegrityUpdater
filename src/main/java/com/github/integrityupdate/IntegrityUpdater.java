package com.github.integrityupdate;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class IntegrityUpdater {
    @Getter private static UpdateChecker updateChecker;
    @Getter private static LoggerUtil loggerUtil;
    @Getter private static Plugin plugin;

    @Getter private static BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if(IntegrityUpdater.adventure == null) {
        throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return IntegrityUpdater.adventure;
    }

    public static void init(Plugin plugin) {
        updateChecker = new UpdateChecker();

        if (plugin == null) {
            Bukkit.getLogger().log(Level.INFO, "[Updater] Init: failed -> plugin cannot be null");
            Bukkit.getPluginManager().disablePlugin(plugin);    
            return;
        }
        IntegrityUpdater.plugin = plugin;
        loggerUtil = new LoggerUtil(plugin);
        adventure = BukkitAudiences.create(plugin);

        Bukkit.getLogger().log(Level.INFO, "[Updater] Init: completed");
    }

    public static void terminate() {
        if(adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    /*private static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }*/
}
