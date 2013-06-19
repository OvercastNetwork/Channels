package com.github.rmsy.channels;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Interface to represent a chat channel.
 */
public interface Channel {
    /**
     * Gets the channel's format.
     *
     * @return The channel's format.
     * @see #setFormat(String)
     */
    @Nonnull
    public String getFormat();

    /**
     * Sets the channel's format (the string that appears before the message).</br><b>Note</b>: <code>%s</code> will be
     * replaced with the sending user's display name. For example, if iamramsey had a display name of 'rmsy', and had a
     * message directed to a channel with a format of <code>[Z] <%s> </code>, his message would be prepended in chat
     * with "[Z] rmsy".
     *
     * @param format The format.
     */
    public void setFormat(@Nonnull String format);

    /**
     * Gets the users who are sending to this channel by default. All users sending to the channel by default must also
     * be listening.
     *
     * @return The users who are sending to this channel by default.
     */
    @Nonnull
    public Set<Player> getMembers();

    /**
     * Gets the users who are listening to messages on this channel.
     *
     * @return The users who are listening to messages on this channel.
     */
    @Nonnull
    public Set<Player> getListeners();

    /**
     * Adds a user as a listener.
     *
     * @param listener The user.
     */
    public void addListener(@Nonnull final Player listener);

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
     * Removes a user as a listener (and as a member, if they are one).
     *
     * @param listener The user.
     */
    public void removeListener(@Nonnull final Player listener);

    /**
     * Sends a new message to the channel.
     *
     * @param format  Whether or not to format the message.
     * @param message The message to be sent.
     * @param sender  The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    public boolean sendMessage(final boolean format, @Nonnull final String message, @Nullable final Player sender);

    /**
     * Gets whether or not the console is listening to this channel.
     *
     * @return Whether or not the console is listening to this channel.
     */
    public boolean isConsoleListening();

    /**
     * Sets whether or not the console is listening to this channel.
     *
     * @param listening Whether or not the console is listening to this channel.
     */
    public void setConsoleListening(boolean listening);
}
