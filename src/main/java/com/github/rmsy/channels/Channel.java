package com.github.rmsy.channels;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;

/** Interface to represent a chat channel. */
public interface Channel {
    /**
     * Gets the channel's format.
     *
     * @return The channel's format.
     * @see #setFormat(String)
     */
    public String getFormat();

    /**
     * Sets the channel's format.
     *
     * @param format The format.
     */
    public void setFormat(String format);
    /**
     * Gets the channel's broadcast format.
     *
     * @return The channel's format.
     * @see #setFormat(String)
     */
    public String getBroadcastFormat();

    /**
     * Sets the channel's broadcast format.
     *
     * @param format The format.
     */
    public void setBroadcastFormat(String format);

    /**
     * Gets the users who are sending to this channel by default.
     *
     * @return The users who are sending to this channel by default.
     */
    public ImmutableSet<String> getMembers();

    /**
     * Sends a new message to the channel.
     *
     * @param message The message to be sent.
     * @param sender  The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    public boolean sendMessage(final String message, @Nullable final Player sender);

    /**
     * Gets the permission node that is required for listening on this channel. Users without this permission node will
     * not receive messages from this channel.
     *
     * @return The permission node that is required for listening on this channel.
     */
    public Permission getListeningPermission();

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    public void broadcast(final String message);
}
