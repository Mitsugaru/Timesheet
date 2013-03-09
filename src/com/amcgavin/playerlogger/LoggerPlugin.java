package com.amcgavin.playerlogger;


import java.util.Date;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

public class LoggerPlugin extends JavaPlugin {

	private HashMap<String, Timesheet> loggingPlayers = new HashMap<String, Timesheet>();
	public SQLDatabase database;
	public LoggerConfig config;
	public int defaultCap;
	public void onDisable() {
		System.out.print(this + " disabled.");	
	}

	public void onEnable() {
		System.out.print(this + " enabled.");
		getServer().getPluginManager().registerEvents(new LogPlayerListener(this), this);

		config = new LoggerConfig();
		config.load();
		database = new SQLDatabase(this);
	}

	public void addToLoggingPlayers(String player, int id, int base, long time) {
		loggingPlayers.put(player, new Timesheet(id, base, time));
	}
	
	public long removeFromLoggingPlayers(String player) {
		Timesheet timesheet = loggingPlayers.get(player);
		loggingPlayers.remove(player);
		return ((new Date().getTime() - timesheet.getTime())/1000 + timesheet.getBase());
	}
	
	public int getId(String player) {
	   int id = -1;
	   Timesheet ts = loggingPlayers.get(player);
	   if(ts != null) {
	      id = ts.getId();
	   }
		return id;
	}
}
