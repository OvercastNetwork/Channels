package com.github.rmsy.channels.event;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.ChannelsEvent;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * Raised when a user is removed as a listener from a channel.
 */
public class ChannelUnListenEvent extends ChannelsEvent implements Cancellable {
    /**
     * The handlers for the event.
     */
    private static final HandlerList handlers = new HandlerList();
    /**
     * The channel.
     */
    @Nonnull
    private final Channel channel;
    /**
     * The player.
     */
    @Nonnull
    private final Player player;
    /**
     * Whether or not the event is cancelled.
     */
    private boolean cancelled;

    private ChannelUnListenEvent() {
        this.channel = null;
        this.player = null;
    }

    /**
     * Creates a new ChannelUnListenEvent.
     *
     * @param channel The channel.
     * @param player  The listener.
     */
    public ChannelUnListenEvent(@Nonnull final Channel channel, @Nonnull final Player player) {
        this.channel = Preconditions.checkNotNull(channel, "channel");
        this.player = Preconditions.checkNotNull(player, "player");
    }

    /**
     * Gets the player.
     *
     * @return The player.
     */
    @Nonnull
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the channel.
     *
     * @return The channel.
     */
    @Nonnull
    public Channel getChannel() {
        return this.channel;
    }

    /**
     * Gets the handlers for the event.
     */
    @Override
    public HandlerList getHandlers() {
        return ChannelUnListenEvent.handlers;
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
