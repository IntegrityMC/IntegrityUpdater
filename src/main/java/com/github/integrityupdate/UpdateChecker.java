package com.github.integrityupdate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateChecker {
    public PluginInfo fetchPluginInfo(String pluginName) {
        try {
            String url = "https://integritymc.github.io/IntegrityUpdater/" + pluginName.toLowerCase() + "/updater.json";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch data: HTTP " + connection.getResponseCode());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                JsonObject json = new Gson().fromJson(reader, JsonObject.class);

                String version = json.get("version").getAsString();
                String mainLink = json.get("main-link").getAsString();

                List<String> description = new ArrayList<>();
                JsonArray descArray = json.getAsJsonArray("description");
                for (int i = 0; i < descArray.size(); i++) {
                    description.add(descArray.get(i).getAsString());
                }

                return new PluginInfo(pluginName, version, mainLink, description);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUpdateNeeded() {
        PluginInfo pluginInfo = fetchPluginInfo(IntegrityUpdater.getPlugin().getDescription().getName());
        return !IntegrityUpdater.getPlugin().getDescription().getVersion().equals(pluginInfo.getVersion());
    }

    public void updateMessage() {
        if (isUpdateNeeded())
            IntegrityUpdater.getLoggerUtil().log(ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, "Please update the plugin §7("+fetchPluginInfo(IntegrityUpdater.getPlugin().getDescription().getName()).getMainLink()+") to "+fetchPluginInfo(IntegrityUpdater.getPlugin().getDescription().getName()).getVersion());
    }

    public void isLatest(Player player) {
        if (isUpdateNeeded()) {
            if (player.hasPermission("integrity.update") || player.isOp()) {
                Audience audience = IntegrityUpdater.getAdventure().player(player);

                audience.sendMessage(Component
                        .text("§3● §bPlease update "+IntegrityUpdater.getPlugin().getDescription().getName()+"!")
                        .clickEvent(ClickEvent.openUrl(fetchPluginInfo(IntegrityUpdater.getPlugin().getDescription().getName()).getMainLink()))
                        .hoverEvent(HoverEvent.showText(Component.text("§7Click here for Update!"))));
            }
        }
    }
}
