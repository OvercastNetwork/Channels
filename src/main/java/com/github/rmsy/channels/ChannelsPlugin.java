package com.github.rmsy.channels;

import com.github.rmsy.channels.impl.SimpleChannel;
import com.github.rmsy.channels.impl.SimplePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.logging.Level;

public class ChannelsPlugin extends JavaPlugin {
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
        this.metrics = null;
        this.playerManager = null;
        this.globalChannel = null;
    }

    @Override
    public void onEnable() {
        this.globalChannel = new SimpleChannel("<%s" + ChatColor.RESET + ">", true, true);
        this.playerManager = new SimplePlayerManager();

        try {
            this.metrics = new Metrics(this);
            this.metrics.start();
            this.getLogger().log(Level.INFO, "Metrics enabled.");
        } catch (IOException exception) {
            this.getLogger().log(Level.WARNING, "An unknown error occurred. Metrics were not started.");
        }
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
