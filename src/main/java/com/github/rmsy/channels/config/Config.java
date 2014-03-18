package com.github.rmsy.channels.config;

import com.github.rmsy.channels.ChannelsPlugin;
import com.sk89q.minecraft.util.commands.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    public static Configuration getConfiguration() {
        ChannelsPlugin plugin = ChannelsPlugin.get();
        if(plugin != null) {
            return plugin.getConfig();
        } else {
            return new YamlConfiguration();
        }
    }

    public static class Chat {
        public static class Admin {
            private static final String PERM_NODE = "channels.admin";

            public static final String PERM_SEND = Admin.PERM_NODE + ".send";
            public static final String PERM_RECEIVE = Admin.PERM_NODE + ".receive";

            public static String switchMessage(boolean success) {
                if (success) {
                    return getConfiguration().getString("admin-chat.switch.success-msg", "Changed default channel to administrator chat.");
                } else {
                    return getConfiguration().getString("admin-chat.switch.no-change-msg", "Administrator chat is already your default channel.");
                }
            }

            public static String format() {
                return ChatColor.translateAlternateColorCodes('`', getConfiguration().getString("admin-chat.format", "`f[`6A`f] {1}`r`f: {2}"));
            }
        }

        public static String messageSuccessMessage() {
            return getConfiguration().getString("admin-chat.message-success-msg", "Message sent.");
        }
    }
}
