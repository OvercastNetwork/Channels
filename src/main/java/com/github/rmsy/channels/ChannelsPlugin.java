package com.github.rmsy.channels;

import com.github.rmsy.channels.command.GlobalChannelCommands;
import com.github.rmsy.channels.impl.SimpleChannel;
import com.github.rmsy.channels.impl.SimplePlayerManager;
import com.github.rmsy.channels.listener.ChatListener;
import com.github.rmsy.channels.listener.PlayerListener;
import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.logging.Level;

public class ChannelsPlugin extends JavaPlugin {
    public static final String GLOBAL_CHANNEL_PARENT_NODE = "channels.global";
    public static final String GLOBAL_CHANNEL_SEND_NODE = ChannelsPlugin.GLOBAL_CHANNEL_PARENT_NODE + ".send";
    public static final String GLOBAL_CHANNEL_RECEIVE_NODE = ChannelsPlugin.GLOBAL_CHANNEL_PARENT_NODE + ".receive";
    /**
     * The plugin instance.
     */
    @Nonnull
    public static ChannelsPlugin plugin;
    /**
     * The global channel.
     */
    @Nonnull
    private Channel globalChannel;
    /**
     * The commands manager.
     */
    @Nonnull
    private CommandsManager commands;
    /**
     * The commands' registration.
     */
    @Nonnull
    private CommandsManagerRegistration commandsRegistration;
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

    /**
     * Gets the universal player manager.
     *
     * @return The universal player manager.
     */
    @Nonnull
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + "Usage: " + e.getUsage());
        } catch (WrappedCommandException e) {
            sender.sendMessage(ChatColor.RED + "An unknown error has occurred. Please notify an administrator.");
            e.printStackTrace();
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        this.metrics = null;
        this.commandsRegistration.unregisterCommands();
        this.commandsRegistration = null;
        this.commands = null;
        this.playerManager = null;
        this.globalChannel = null;
    }

    @Override
    public void onEnable() {
        ChannelsPlugin.plugin = this;

        this.globalChannel = new SimpleChannel("<%s" + ChatColor.RESET + ">: ", true, ChannelsPlugin.GLOBAL_CHANNEL_RECEIVE_NODE);
        this.playerManager = new SimplePlayerManager();
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        this.commands = new BukkitCommandsManager();
        this.commandsRegistration = new CommandsManagerRegistration(this, this.commands);
        this.commandsRegistration.register(GlobalChannelCommands.class);

        try {
            this.metrics = new Metrics(this);
            this.metrics.start();
            this.getLogger().log(Level.INFO, "Metrics enabled.");
        } catch (IOException exception) {
            this.getLogger().log(Level.WARNING, "An unknown error occurred. Metrics were not started.");
        }
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
