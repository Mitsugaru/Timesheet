package com.amcgavin.playerlogger;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogPlayerListener implements Listener {

	private LoggerPlugin plugin;
	
	public LogPlayerListener(LoggerPlugin instance) {
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.database.startLogging(event.getPlayer().getName());	
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.database.stopLogging(event.getPlayer().getName());
	}
	
	

}

