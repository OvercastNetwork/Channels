package com.github.rmsy.channels.listener;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.rmsy.channels.ChannelsAPI;
import com.google.common.base.Preconditions;

/**
 * Listener for chat-related events.
 */
public class ChatListener implements Listener{

	/**
	 * The plugin.
	 */
	private final ChannelsAPI plugin;

	/**
	 * Creates a new ChatListener.
	 * 
	 * @param api the API instance
	 */
	public ChatListener(@Nonnull final ChannelsAPI api){
		this.plugin = Preconditions.checkNotNull(api, "plugin");
	}

	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerChat(@Nonnull final AsyncPlayerChatEvent event){
		Preconditions.checkNotNull(event, "event").setCancelled(true);
		Player sender = event.getPlayer();
		this.plugin.getPlayerManager().getMembershipChannel(sender).sendMessage(true, event.getMessage(), sender);
	}
}
