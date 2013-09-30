package com.github.rmsy.channels;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Represents a channel-related event. */
public abstract class ChannelsEvent extends Event {
    public abstract HandlerList getHandlers();
}
