package com.github.rmsy.channels;

import com.github.rmsy.channels.impl.SimpleChannel;
import com.github.rmsy.channels.impl.SimplePlayerManager;
import com.github.rmsy.channels.listener.ChatListener;
import com.github.rmsy.channels.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.logging.Level;

public class ChannelsPlugin extends JavaPlugin {
    public static final String GLOBAL_CHANNEL_PERMISSION = "channels.global";
    /**
     * The global channel.
     */
    @Nonnull
    private Channel globalChannel;
    /**
     * The MCStats metrics instance. Null if creation failed.
     */
    @Nullable
    private Metrics metrics;
    /**
     * The player manager.
     */
    @Nonnull
    private PlayerManager playerManager;

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        this.metrics = null;
        this.playerManager = null;
        this.globalChannel = null;
    }

    @Override
    public void onEnable() {
        this.globalChannel = new SimpleChannel("<%s" + ChatColor.RESET + ">: ", true, ChannelsPlugin.GLOBAL_CHANNEL_PERMISSION);
        this.playerManager = new SimplePlayerManager();
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        try {
            this.metrics = new Metrics(this);
            this.metrics.start();
            this.getLogger().log(Level.INFO, "Metrics enabled.");
        } catch (IOException exception) {
            this.getLogger().log(Level.WARNING, "An unknown error occurred. Metrics were not started.");
        }

        Bukkit.getConsoleSender().addAttachment(this, ChannelsPlugin.GLOBAL_CHANNEL_PERMISSION, true);
    }

    /**
     * Gets the universal player manager.
     *
     * @return The universal player manager.
     */
    @Nonnull
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    /**
     * Gets the global channel.
     *
     * @return The global channel.
     */
    @Nonnull
    public Channel getGlobalChannel() {
        return this.globalChannel;
    }
}
