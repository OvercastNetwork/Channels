package com.github.rmsy.channels.listener;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsPlugin;
import com.github.rmsy.channels.PlayerManager;
import com.github.rmsy.channels.setting.DefaultChannelOptions;
import com.github.rmsy.channels.setting.Settings;
import com.google.common.base.Preconditions;
import me.anxuiz.settings.bukkit.PlayerSettings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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

        DefaultChannelOptions defaultChannelPreference = (DefaultChannelOptions) PlayerSettings.getManager(player).getValue(Settings.DEFAULT_CHANNEL);
        Channel newChannel = null;

        switch(defaultChannelPreference) {
            case GLOBAL_CHAT:
                newChannel = this.plugin.getGlobalChannel();
                break;
            case ADMIN_CHAT:
                newChannel = this.plugin.getAdminChannel();
                break;
            // Let PGM handle team-chat
            case TEAM_CHAT:
            case NONE:
            default:
                break;
        }

        if (manager.getMembershipChannel(player) == null) {
            if(newChannel != null) {
                if (player.hasPermission(newChannel.getListeningPermission())) {
                    manager.setMembershipChannel(player, this.plugin.getDefaultChannel());
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to join this channel.");
                    manager.setMembershipChannel(player, this.plugin.getDefaultChannel());
                }
            } else {
                manager.setMembershipChannel(player, this.plugin.getDefaultChannel());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.plugin.getPlayerManager().removePlayer(event.getPlayer());
    }
}
