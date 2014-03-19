package com.github.rmsy.channels.setting;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsPlugin;
import com.github.rmsy.channels.PlayerManager;
import com.google.common.base.Preconditions;
import com.sk89q.minecraft.util.commands.ChatColor;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.bukkit.PlayerSettingCallback;
import me.anxuiz.settings.bukkit.PlayerSettings;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class DefaultChannelCallback extends PlayerSettingCallback {

    private ChannelsPlugin plugin;
    private PlayerManager playerManager;

    public DefaultChannelCallback(ChannelsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "plugin");
        Preconditions.checkArgument(plugin.isEnabled(), "plugin not enabled");

        this.plugin = plugin;
        this.playerManager = this.plugin.getPlayerManager();
    }

    @Override
    public void notifyChange(@Nonnull Player player, @Nonnull Setting setting, @Nonnull Object o, @Nonnull Object o2) {
        DefaultChannelOptions newChanelType = (DefaultChannelOptions) o2;
        Channel newChannel = null;

        switch (newChanelType) {
            case GLOBAL_CHAT:
                newChannel = this.plugin.getGlobalChannel();
                break;
            case ADMIN_CHAT:
                newChannel = this.plugin.getAdminChannel();
                break;
            // Let PGM handle this
            case TEAM_CHAT:
            case NONE:
                break;
        }

        if(newChannel != null) {
            if(player.hasPermission(newChannel.getListeningPermission())) {
                this.playerManager.setMembershipChannel(player, newChannel);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to join this channel.");
                PlayerSettings.getManager(player).setValue(setting, DefaultChannelOptions.GLOBAL_CHAT);
            }
        }
    }
}
