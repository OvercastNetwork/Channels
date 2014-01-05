package com.github.rmsy.channels.listener;

import com.github.rmsy.channels.ChannelsPlugin;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/** Listener for chat-related events. */
public class ChatListener implements Listener {

    /** The plugin. */
    private final ChannelsPlugin plugin;

    private ChatListener() {
        this.plugin = null;
    }

    /**
     * Creates a new ChatListener.
     *
     * @param plugin The plugin.
     */
    public ChatListener(final ChannelsPlugin plugin) {
        this.plugin = Preconditions.checkNotNull(plugin, "plugin");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        Preconditions.checkNotNull(event, "event").setCancelled(true);
        Player sender = event.getPlayer();
        this.plugin.getPlayerManager().getMembershipChannel(sender).sendMessage(event.getMessage(), sender);
    }
}
