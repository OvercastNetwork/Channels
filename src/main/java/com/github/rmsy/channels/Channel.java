package com.github.rmsy.channels;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;

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
     * Sets the channel's format (the string that appears before the message).</br><b>Note</b>: <code>%s</code> will be
     * replaced with the sending user's display name. For example, if iamramsey had a display name of 'rmsy', and had a
     * message directed to a channel with a format of <code>[Z] <%s> </code>, his message would be prepended in chat
     * with "[Z] rmsy".
     *
     * @param format The format.
     */
    public void setFormat(String format);

    /**
     * Gets the users who are sending to this channel by default.
     *
     * @return The users who are sending to this channel by default.
     */

    public ImmutableSet<Player> getMembers();

    /**
     * Gets whether or not messages sent are stripped of color.
     *
     * @return Whether or not messages sent are stripped of color.
     */
    public boolean shouldStripColors();

    /**
     * Sets whether or not messages sent are stripped of color.
     *
     * @param shouldStripColors Whether or not messages sent are stripped of color.
     */
    public void shouldStripColors(boolean shouldStripColors);

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

    public String getListeningPermission();

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    public void broadcast(final String message);
}
