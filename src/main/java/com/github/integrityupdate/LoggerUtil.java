package com.github.integrityupdate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class LoggerUtil {
    private Plugin plugin; 

    public LoggerUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public void log(ChatColor primary, ChatColor secondary, String message) {
        Bukkit.getConsoleSender().sendMessage(primary+"["+plugin.getDescription().getName()+"] "+secondary+message);
    }
}
