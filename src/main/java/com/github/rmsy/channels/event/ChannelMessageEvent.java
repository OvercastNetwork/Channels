package com.github.rmsy.channels.event;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsEvent;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import javax.annotation.Nullable;

/** Raised when a message is sent to a channel. */
public final class ChannelMessageEvent extends ChannelsEvent implements Cancellable {
    /** The handlers for the event. */
    private static final HandlerList handlers = new HandlerList();
    /** The {@link Channel} to which the message was sent. */
    private final Channel channel;
    /** The message sender, or null for console. */
    private @Nullable final Player sender;
    /** The message to be sent. */
    private String message;
    /** Whether or not the event is cancelled. */
    private boolean cancelled = false;

    /**
     * Creates a new ChannelMessageEvent.
     *
     * @param message The message.
     * @param sender  The sender, or null for console.
     */
    public ChannelMessageEvent(String message, @Nullable final Player sender, Channel channel) {
        this.message = Preconditions.checkNotNull(message, "message");
        this.sender = sender;
        this.channel = Preconditions.checkNotNull(channel, "Channel");
    }

    /**
     * Gets the message sender, or null for console.
     *
     * @return The message sender, or null for console.
     */
    public @Nullable Player getSender() {
        return this.sender;
    }

    /**
     * Gets the message to be sent.
     *
     * @return The message to be sent.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to be sent.
     *
     * @param message The message to be sent.
     */
    public void setMessage(String message) {
        this.message = Preconditions.checkNotNull(message, "message");
    }

    /**
     * Gets the {@link Channel} to which this message was sent.
     *
     * @return The {@link Channel}.
     */
    public Channel getChannel() {
        return this.channel;
    }

    /** Gets the handlers for the event. */
    @Override
    public HandlerList getHandlers() {
        return ChannelMessageEvent.handlers;
    }

    /** Gets the handlers for the event. */
    public static HandlerList getHandlerList() {
        return ChannelMessageEvent.handlers;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
     * pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
     * pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
