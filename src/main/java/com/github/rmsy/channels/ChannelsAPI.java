package com.github.rmsy.channels;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.mcstats.Metrics;

import com.github.rmsy.channels.impl.SimpleChannel;
import com.github.rmsy.channels.impl.SimplePlayerManager;
import com.github.rmsy.channels.listener.ChatListener;
import com.github.rmsy.channels.listener.PlayerListener;

/**
 * Represents a channel API instance. This is used to translate all
 * calls to the API easily.<br>
 * <br>
 * To use this API, use this code:<br>
 * <code>
 * public void onEnable(){<br>
 * ChannelsAPI api = new ChannelsAPI(this);<br>
 * }
 * </code>
 * 
 * @author turt2live
 */
public class ChannelsAPI implements Listener{

	public static final String GLOBAL_CHANNEL_PERMISSION = "channels.global";

	/**
	 * The global channel.
	 */
	@Nonnull
	private Channel globalChannel;

	/**
	 * The MCStats metrics instance. Null if creation failed.
	 */
	@Nullable
	private Metrics metrics;

	/**
	 * The player manager.
	 */
	@Nonnull
	private PlayerManager playerManager;

	/**
	 * The hosted plugin
	 */
	@Nonnull
	private Plugin hoster;

	/**
	 * Creates a new Channels API. This will attempt to setup the listeners and
	 * resources needed to run the API
	 * 
	 * @param plugin the plugin that is requesting access to this API
	 */
	public ChannelsAPI(@Nonnull Plugin plugin){
		this.globalChannel = new SimpleChannel("<%s" + ChatColor.RESET + ">: ", true, ChannelsAPI.GLOBAL_CHANNEL_PERMISSION);
		this.playerManager = new SimplePlayerManager();
		plugin.getServer().getPluginManager().registerEvents(new ChatListener(this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new PlayerListener(this), plugin);

		// Start MCStats (Metrics)
		try{
			this.metrics = new Metrics(new ChannelsAPIPlugin(this.hoster));
			this.metrics.start();
			this.hoster.getLogger().info("[Channels] Metrics started");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void disable(){
		HandlerList.unregisterAll(this);
		this.metrics = null;
		this.playerManager = null;
		this.globalChannel = null;
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event){
		if(event != null){
			Plugin pl = event.getPlugin();
			if(pl.getName().equals(this.hoster.getName())
					&& pl.getDescription().getMain().equals(this.hoster.getDescription().getMain())
					&& pl.getDescription().getVersion().equals(this.hoster.getDescription().getVersion())){
				disable();
			}
		}
	}

	/**
	 * Gets the active player manager
	 * 
	 * @return the active player manager, can be null if the host plugin was disabled
	 */
	public PlayerManager getPlayerManager(){
		return this.playerManager;
	}

	/**
	 * Gets the active global channel
	 * 
	 * @return the active global channel, can be null if the host plugin was disabled
	 */
	public Channel getGlobalChannel(){
		return this.globalChannel;
	}

	/**
	 * Gets the plugin who hosts this API instance
	 * 
	 * @return the hosting plugin
	 */
	public Plugin getHost(){
		return this.hoster;
	}

}
