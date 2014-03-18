package com.github.rmsy.channels.command;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsPlugin;
import com.github.rmsy.channels.PlayerManager;
import com.github.rmsy.channels.config.Config;
import com.google.common.base.Preconditions;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class AdminChannelCommands {

    @Command(
        aliases = "a",
        desc = "Sends a message to the administrator channel (or sets the administrator channel to your default channel).",
        max = -1,
        min = 0,
        anyFlags = true,
        usage = "[message...]"
    )
    @Console
    @CommandPermissions({Config.Chat.Admin.PERM_SEND, Config.Chat.Admin.PERM_RECEIVE})
    public static void onAdminChatCommand(@Nonnull final CommandContext arguments, @Nonnull final CommandSender sender) throws CommandException {
        if (Preconditions.checkNotNull(arguments, "arguments").argsLength() == 0) {
            if (Preconditions.checkNotNull(sender, "sender").hasPermission(Config.Chat.Admin.PERM_RECEIVE)) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerManager playerManager = ChannelsPlugin.get().getPlayerManager();
                    Channel oldChannel = playerManager.getMembershipChannel(player);
                    Channel adminChannel = ChannelsPlugin.get().getAdminChannel();
                    playerManager.setMembershipChannel(player, adminChannel);
                    if (!oldChannel.equals(adminChannel)) {
                        sender.sendMessage(ChatColor.YELLOW + Config.Chat.Admin.switchMessage(true));
                    } else {
                        throw new CommandException(Config.Chat.Admin.switchMessage(false));
                    }
                } else {
                    throw new CommandUsageException("You must provide a message.", "/a <message...>");
                }
            } else {
                throw new CommandPermissionsException();
            }
        } else if (Preconditions.checkNotNull(sender, "sender").hasPermission(Config.Chat.Admin.PERM_SEND)) {
            Player sendingPlayer = null;
            if (sender instanceof Player) {
                sendingPlayer = (Player) sender;
            }
            ChannelsPlugin.get().getAdminChannel().sendMessage(arguments.getJoinedStrings(0), sendingPlayer);
            if (!sender.hasPermission(Config.Chat.Admin.PERM_RECEIVE)) {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + Config.Chat.messageSuccessMessage());
            }
        } else {
            throw new CommandPermissionsException();
        }
    }
}
