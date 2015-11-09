package com.github.rmsy.channels;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;

/** Interface to represent a chat channel. */
public interface Channel {


    /**
     * Creates the channel's format.
     *
     * @param message the message.
     * @param sender the sender, null if console.
     * @param receiver the receiver.
     * @param broadcast whether this is a broadcast.
     * @return the formatted component.
     */
    BaseComponent getFormat(final BaseComponent message, @Nullable final Player sender, final CommandSender receiver, boolean broadcast);

    /**
     * Gets the users who are sending to this channel by default.
     *
     * @return The users who are sending to this channel by default.
     */
    ImmutableSet<String> getMembers();

    /**
     * Sends a new message to the channel.
     *
     * @param message The message to be sent.
     * @param sender  The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    boolean sendMessage(final BaseComponent message, @Nullable final Player sender);

    /**
     * Sends a new message to the channel.
     *
     * @param message The message to be sent.
     * @param sender  The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    boolean sendMessage(String message, @Nullable final Player sender);

    /**
     * Gets the permission node that is required for listening on this channel. Users without this permission node will
     * not receive messages from this channel.
     *
     * @return The permission node that is required for listening on this channel.
     */
    Permission getListeningPermission();

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    void broadcast(final BaseComponent message);

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    void broadcast(String message);

}
