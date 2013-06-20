package com.github.rmsy.channels.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.github.rmsy.channels.ChannelsEvent;
import com.google.common.base.Preconditions;

/**
 * Raised when a message is sent to a channel.
 */
public final class ChannelMessageEvent extends ChannelsEvent implements Cancellable{
	/**
	 * The handlers for the event.
	 */
	private static final HandlerList handlers = new HandlerList();
	/**
	 * The message sender, or null for console.
	 */
	@Nullable
	private final Player sender;
	/**
	 * The message to be sent.
	 */
	@Nonnull
	private String message;
	/**
	 * Whether or not the event is cancelled.
	 */
	private boolean cancelled = false;

	/**
	 * Creates a new ChannelMessageEvent.
	 * 
	 * @param message The message.
	 * @param sender The sender, or null for console.
	 */
	public ChannelMessageEvent(@Nonnull String message, @Nullable final Player sender){
		this.message = Preconditions.checkNotNull(message, "message");
		this.sender = sender;
	}

	/**
	 * Gets the message sender, or null for console.
	 * 
	 * @return The message sender, or null for console.
	 */
	@Nullable
	public Player getSender(){
		return this.sender;
	}

	/**
	 * Gets the message to be sent.
	 * 
	 * @return The message to be sent.
	 */
	@Nonnull
	public String getMessage(){
		return message;
	}

	/**
	 * Sets the message to be sent.
	 * 
	 * @param message The message to be sent.
	 */
	public void setMessage(@Nonnull String message){
		this.message = Preconditions.checkNotNull(message, "message");
	}

	/**
	 * Gets the handlers for the event.
	 */
	@Override
	public HandlerList getHandlers(){
		return ChannelMessageEvent.handlers;
	}

	/**
	 * Gets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
	 * pass to other plugins
	 * 
	 * @return true if this event is cancelled
	 */
	@Override
	public boolean isCancelled(){
		return this.cancelled;
	}

	/**
	 * Sets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
	 * pass to other plugins.
	 * 
	 * @param cancel true if you wish to cancel this event
	 */
	@Override
	public void setCancelled(boolean cancel){
		this.cancelled = cancel;
	}
}
