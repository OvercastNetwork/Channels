package com.github.rmsy.channels.impl;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.event.ChannelMessageEvent;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of {@link Channel}. <p> This implementation of {@link Channel} supports several different
 * custom format variables: <table border="1"> <tbody> <tr> <th id="var">Variable</th> <th id="expl">Meaning</th> <th
 * id="ex">Examples</th> </tr> <tr> <td headers="var">{0}</td> <td headers="expl"> The sending {@link Player}'s name (or
 * "Console", if the sending {@link Player} is <code>null</code>. </td> <td headers="ex"> <ul> <li> If player
 * "iamramsey" sent a message, <code>{0}</code> would evaluate to "iamramsey". </li> <li> If a message was sent with a
 * <code>null</code> sender, <code>{0}</code> would evaluate to "Console". </li> </ul> </td> </tr> <tr> <td
 * headers="var">{1}</td> <td headers="expl"> The sending {@link Player}'s display name (or "§3*§6Console", if the
 * sending {@link Player} is <code>null</code>. </td> <td headers="ex"> <ul> <li> If player "iamramsey" (with display
 * name "§ciamramsey") sent a message, <code>{1}</code> would evaluate to "§ciamramsey". </li> <li> If a message was
 * sent with a <code>null</code> sender, <code>{1}</code> would evaluate to "§3*§6Console". </li> </ul> </td> </tr> <tr>
 * <td headers="var">{2}</td> <td headers="expl">The raw message.</td> <td headers="ex"> <ul> <li>If the message
 * "§cHello!" is sent, <code>{2}</code> would evaluate to "§cHello!".</li> </ul> </td> </tr> <tr> <td
 * headers="var">{3}</td> <td headers="expl"> The color-filtered message. The message is passed through {@link
 * ChatColor#stripColor}, removing any color codes. </td> <td headers="ex"> <ul> <li>If the message "§cHello!" is sent,
 * <code>{3}</code> would evaluate to "Hello!".</li> </ul> </td> </tr> </tbody> </table> <br/> </p>
 */
public class SimpleChannel implements Channel {

    /** The members of the channel (stored by their names) */
    private final Set<String> members;
    /** The permission node that will be broadcast from this channel to. */
    private final Permission permission;
    /** The format. */
    private String format;
    private String broadcastFormat;

    private SimpleChannel() {
        this.members = null;
        this.permission = null;
    }

    /**
     * Creates a new SimpleChannel.
     *
     * @param format     The format to be applied to messages.
     * @param permission The permission node that will be broadcast from this channel to.
     * @see SimpleChannel for detailed formatting information.
     */
    public SimpleChannel(final String format, final String broadcastFormat, final Permission permission) {
        this.format = Preconditions.checkNotNull(format, "format");
        this.broadcastFormat = Preconditions.checkNotNull(broadcastFormat, "broadcast format");
        this.permission = Preconditions.checkNotNull(permission);
        this.members = new HashSet<>();
    }

    public SimpleChannel(final String format, final Permission permission) {
        this(format, format, permission);
    }

    /**
     * Gets the channel's format.
     *
     * @return The channel's format.
     * @see #setFormat(String)
     */
    @Override
    public String getFormat() {
        return this.format;
    }

    /**
     * Sets the channel's format (the string that appears before the message).
     *
     * @param format The format.
     * @see SimpleChannel for detailed formatting information.
     */
    @Override
    public void setFormat(String format) {
        this.format = Preconditions.checkNotNull(format, "format");
    }

    @Override
    public String getBroadcastFormat() {
        return this.broadcastFormat;
    }

    @Override
    public void setBroadcastFormat(String format) {
        this.broadcastFormat = Preconditions.checkNotNull(format, "format");
    }

    /**
     * Gets the users who are sending to this channel by default.
     *
     * @return The users who are sending to this channel by default.
     */
    @Override
    public final ImmutableSet<String> getMembers() {
        return ImmutableSet.copyOf(this.members);
    }

    /**
     * Sends a new message to the channel.
     *
     * @param rawMessage The message to be sent.
     * @param sender     The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    @Override
    public boolean sendMessage(String rawMessage, @Nullable Player sender) {
        ChannelMessageEvent event = new ChannelMessageEvent(rawMessage, sender, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return false;
        }

        String sanitizedMessage = ChatColor.stripColor(Preconditions.checkNotNull(rawMessage, "Message"));

        this.sendMessageToViewer(sender, Bukkit.getConsoleSender(), sanitizedMessage, event);

        for(Player viewer : Bukkit.getOnlinePlayers()) {
            if(viewer.hasPermission(this.permission)) {
                this.sendMessageToViewer(sender, viewer, sanitizedMessage, event);
            }
        }

        return true;
    }

    public void sendMessageToViewer(Player sender, CommandSender viewer, String sanitizedMessage, ChannelMessageEvent event) {
        boolean senderPresent = sender != null;

        String senderName = senderPresent ? sender.getName(viewer) : "Console";
        String senderDisplayName = senderPresent ? sender.getDisplayName(viewer) : ChatColor.GOLD + "*" + ChatColor.AQUA + "Console";
        String format = senderPresent ? this.format : this.broadcastFormat;

        viewer.sendMessage(MessageFormat.format(
                format,
                senderName,
                senderDisplayName,
                event.getMessage(),
                sanitizedMessage
        ));
    }

    /**
     * Gets the permission node that is required for listening on this channel. Users without this permission node will
     * not receive messages from this channel.
     *
     * @return The permission node that is required for listening on this channel.
     */
    @Override
    public Permission getListeningPermission() {
        return this.permission;
    }

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    @Override
    public void broadcast(final String message) {
        this.sendMessage(message, null);
    }

    /**
     * Removes a user as a member.</br><b>Caution</b>: Only invoke this when the user has been (or will be) assigned as
     * a member to another channel.
     *
     * @param member The user.
     */
    protected final void removeMember(Player member) {
        this.members.remove(Preconditions.checkNotNull(member, "member"));
    }

    /**
     * Adds a user as a member.</br><b>Caution</b>: Only invoke this when the user has been (or will be) removed as a
     * member from their existing channel (if applicable).
     *
     * @param member The user.
     */
    protected final void addMember(Player member) {
        this.members.add(Preconditions.checkNotNull(member, "member").getName());
    }
}
