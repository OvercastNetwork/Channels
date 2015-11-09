package com.github.rmsy.channels.impl;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.event.ChannelMessageEvent;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of {@link Channel}.
 */
public abstract class SimpleChannel implements Channel {

    /** The members of the channel (stored by their names) */
    private final Set<String> members;
    /** The permission node that will be broadcast from this channel to. */
    private final Permission permission;

    /**
     * Creates a new SimpleChannel.
     *
     * @param permission The permission node that will be broadcast from this channel to.
     * @see SimpleChannel for detailed formatting information.
     */
    public SimpleChannel(final Permission permission) {
        this.permission = Preconditions.checkNotNull(permission);
        this.members = new HashSet<>();
    }

    /**
     * Create the channel's format.
     * This is called every time a message is about to be sent.
     *
     * @param message the message.
     * @param sender the sender, null if console.
     * @param receiver the receiver.
     * @param broadcast whether this is a broadcast message.
     * @return the formatted component message.
     * @see #sendMessageToViewer(Player, CommandSender, ChannelMessageEvent, boolean)
     */
    public abstract BaseComponent getFormat(BaseComponent message, @Nullable Player sender, CommandSender receiver, boolean broadcast);

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
     * @param message The message to be sent.
     * @param sender The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    @Override
    public boolean sendMessage(BaseComponent message, @Nullable Player sender) {
        return sendMessageToAll(message, sender, false);
    }

    /**
     * Sends a new message to the channel.
     *
     * @param message The message to be sent.
     * @param sender The message sender, or null for console.
     * @return Whether or not the message was sent.
     */
    @Override
    public boolean sendMessage(String message, @Nullable Player sender) {
        return sendMessage(new TextComponent(message), sender);
    }

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    @Override
    public void broadcast(final BaseComponent message) {
        this.sendMessageToAll(message, null, true);
    }

    /**
     * Broadcasts a message to the channel.
     *
     * @param message The message to be broadcast.
     */
    @Override
    public void broadcast(final String message) {
        this.broadcast(new TextComponent(message));
    }

    public boolean sendMessageToAll(BaseComponent message, @Nullable Player sender, boolean broadcast) {
        ChannelMessageEvent event = new ChannelMessageEvent(message, sender, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return false;
        }

        this.sendMessageToViewer(sender, Bukkit.getConsoleSender(), event, broadcast);

        for(Player viewer : Bukkit.getOnlinePlayers()) {
            if(viewer.hasPermission(this.permission)) {
                this.sendMessageToViewer(sender, viewer, event, broadcast);
            }
        }

        return true;
    }


    public void sendMessageToViewer(Player sender, CommandSender viewer, ChannelMessageEvent event, boolean broadcast) {
        BaseComponent component = getFormat(event.getMessage(), sender, viewer, broadcast);
        if(viewer instanceof Player) {
            ((Player) viewer).sendMessage(component);
        } else {
            viewer.sendMessage(component.toLegacyText());
        }
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
     * Removes a user as a member.</br><b>Caution</b>: Only invoke this when the user has been (or will be) assigned as
     * a member to another channel.
     *
     * @param member The user.
     */
    protected final void removeMember(Player member) {
        this.members.remove(Preconditions.checkNotNull(member, "member").getName());
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
