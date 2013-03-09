package com.amcgavin.playerlogger;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoggerConfig {
	private FileConfiguration config = new YamlConfiguration();
	
	private String sqlDatabase;
	private String sqlUser;
	private String sqlPass;
	private String sqlTable;
	public LoggerConfig()
	{

	}
	public void load()
	{
		try {
			config.load(new File("plugins/Timesheet/config.yml"));
		} catch (FileNotFoundException e) {
			loadDefaults();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		sqlDatabase = config.getString("mysql.database");
		sqlUser = config.getString("mysql.username");
		sqlPass = config.getString("mysql.password");
		sqlTable = config.getString("mysql.table");
		
	}
	
	private void loadDefaults()
	{
		System.out.print("[Logger] Default configuration made.");
		sqlDatabase = "localhost:3306/minecraft";
		sqlUser = "root";
		sqlPass = "root";
		sqlTable = "player-logs";
		save();
	}
	
	public void save()
	{
		File file = new File("plugins/Timesheet");
		file.mkdir();
		config.set("mysql.database", sqlDatabase);
		config.set("mysql.username", sqlUser);
		config.set("mysql.password", sqlPass);
		config.set("mysql.table", sqlTable);
		try {
			config.save(new File("plugins/Timesheet/config.yml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDatabase()
	{
		return sqlDatabase;
	}
	
	public String getUsername()
	{
		return sqlUser;
	}
	
	public String getPassword()
	{
		return sqlPass;
	}
	
	public String getTable() {
		return sqlTable;
	}
}
