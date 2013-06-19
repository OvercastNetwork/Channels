package com.github.rmsy.channels.listener;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsPlugin;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;

/**
 * Listener for player-related events.
 */
public class PlayerListener implements Listener {

    /**
     * The plugin.
     */
    private final ChannelsPlugin plugin;

    private PlayerListener() {
        this.plugin = null;
    }

    /**
     * Creates a new PlayerListener.
     *
     * @param plugin The plugin.
     */
    public PlayerListener(@Nonnull final ChannelsPlugin plugin) {
        this.plugin = Preconditions.checkNotNull(plugin, "plugin");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(@Nonnull final PlayerJoinEvent event) {
        Channel globalChannel = this.plugin.getGlobalChannel();
        Player player = Preconditions.checkNotNull(event, "event").getPlayer();
        this.plugin.getPlayerManager().setMembershipChannel(player, globalChannel);
        globalChannel.addListener(player);
    }
}
