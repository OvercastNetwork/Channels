package com.github.rmsy.channels.command;

import com.github.rmsy.channels.ChannelsPlugin;
import com.google.common.base.Preconditions;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Commands for dealing with the global channel.
 */
public final class GlobalChannelCommands {

    @Command(
            aliases = {"g"},
            desc = "Sends a message to the global channel (or sets the global channel to your default channel).",
            max = -1,
            min = 0,
            usage = "[message]"
    )
    @CommandPermissions({ChannelsPlugin.GLOBAL_CHANNEL_RECEIVE_NODE, ChannelsPlugin.GLOBAL_CHANNEL_SEND_NODE})
    @Console
    public static void globalChannelCommand(@Nonnull final CommandContext arguments, @Nonnull final CommandSender sender) throws CommandException {
        if (Preconditions.checkNotNull(arguments, "arguments").argsLength() == 0) {
            if (Preconditions.checkNotNull(sender, "sender").hasPermission(ChannelsPlugin.GLOBAL_CHANNEL_RECEIVE_NODE)) {
                if (sender instanceof Player) {
                    ChannelsPlugin.plugin.getPlayerManager().setMembershipChannel((Player) sender, ChannelsPlugin.plugin.getGlobalChannel());
                    sender.sendMessage(ChatColor.YELLOW + "Switched to global chat.");
                } else {
                    throw new CommandUsageException("You must provide a message.", "/g <message>");
                }
            } else {
                throw new CommandPermissionsException();
            }
        } else if (Preconditions.checkNotNull(sender, "sender").hasPermission(ChannelsPlugin.GLOBAL_CHANNEL_SEND_NODE)) {
            Player sendingPlayer = null;
            if (sender instanceof Player) {
                sendingPlayer = (Player) sender;
            }
            ChannelsPlugin.plugin.getGlobalChannel().sendMessage(arguments.getJoinedStrings(0), sendingPlayer);
            if (!sender.hasPermission(ChannelsPlugin.GLOBAL_CHANNEL_RECEIVE_NODE)) {
                sender.sendMessage(ChatColor.YELLOW + "Message sent.");
            }
        } else {
            throw new CommandPermissionsException();
        }
    }
}
