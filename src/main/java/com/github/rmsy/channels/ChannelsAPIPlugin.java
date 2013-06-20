package com.github.rmsy.channels;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import com.avaje.ebean.EbeanServer;

class ChannelsAPIPlugin extends PluginBase{

	private Plugin plugin;
	private PluginDescriptionFile file = new PluginDescriptionFile("EMetrics", "1.0.0", "com.github.rmsy.channels");

	ChannelsAPIPlugin(Plugin plugin){
		if(plugin == null){
			throw new IllegalArgumentException("Plugin cannot be null!");
		}
		this.plugin = plugin;
	}

	String capitalFirst(String name){
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	public String getRealPluginName(){
		return plugin.getName();
	}

	@Override
	public File getDataFolder(){
		return plugin.getDataFolder();
	}

	@Override
	public PluginDescriptionFile getDescription(){
		return file;
	}

	@Override
	public FileConfiguration getConfig(){
		return plugin.getConfig();
	}

	@Override
	public InputStream getResource(String filename){
		return plugin.getResource(filename);
	}

	@Override
	public void saveConfig(){
		plugin.saveConfig();
	}

	@Override
	public void saveDefaultConfig(){
		plugin.saveDefaultConfig();
	}

	@Override
	public void saveResource(String resourcePath, boolean replace){
		plugin.saveResource(resourcePath, replace);
	}

	@Override
	public void reloadConfig(){
		plugin.reloadConfig();
	}

	@Override
	public PluginLoader getPluginLoader(){
		return plugin.getPluginLoader();
	}

	@Override
	public Server getServer(){
		return plugin.getServer();
	}

	@Override
	public boolean isEnabled(){
		return true;
	}

	@Override
	public void onDisable(){}

	@Override
	public void onLoad(){}

	@Override
	public void onEnable(){}

	@Override
	public boolean isNaggable(){
		return plugin.isNaggable();
	}

	@Override
	public void setNaggable(boolean canNag){
		plugin.setNaggable(canNag);
	}

	@Override
	public EbeanServer getDatabase(){
		return plugin.getDatabase();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		return plugin.getDefaultWorldGenerator(worldName, id);
	}

	@Override
	public Logger getLogger(){
		return Logger.getLogger("channels.rmsy");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		return plugin.onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		return plugin.onCommand(sender, command, label, args);
	}

}
