package com.github.rmsy.channels.listener;

import com.github.rmsy.channels.ChannelsPlugin;
import com.github.rmsy.channels.PlayerManager;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/** Listener for player-related events. */
public class PlayerListener implements Listener {

    /** The plugin. */
    private final ChannelsPlugin plugin;

    private PlayerListener() {
        this.plugin = null;
    }

    /**
     * Creates a new PlayerListener.
     *
     * @param plugin The plugin.
     */
    public PlayerListener(final ChannelsPlugin plugin) {
        this.plugin = Preconditions.checkNotNull(plugin, "plugin");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerManager manager = this.plugin.getPlayerManager();
        if (manager.getMembershipChannel(player) == null) {
            manager.setMembershipChannel(player, this.plugin.getDefaultChannel());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.plugin.getPlayerManager().removePlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(final PlayerKickEvent event) {
        this.plugin.getPlayerManager().removePlayer(event.getPlayer());
    }
}
