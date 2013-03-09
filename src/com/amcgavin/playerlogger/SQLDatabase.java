package com.amcgavin.playerlogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQLDatabase {

	private LoggerPlugin plugin;
	private Connection conn = null;
	private Logger log = Logger.getLogger("Minecraft");
	public SQLDatabase(LoggerPlugin instance) {
		plugin = instance;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+plugin.config.getDatabase()+"?autoReconnect=true&user="+plugin.config.getUsername()+"&password="+plugin.config.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "[logger] some shit happened with mysql.");
		}
		createTables();
	}
	
	public void startLogging(String player) {
		int totaltime = -1;
		int id = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		// check if there is an existing entry in the database
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = conn.prepareStatement("SELECT * FROM `"+plugin.config.getTable()+"` WHERE `day`=? AND `player`=?");
			ps.setString(1, date);
			ps.setString(2, player);
			rs = ps.executeQuery();
			while(rs.next()) {
				totaltime = rs.getInt("totaltime");
				id = rs.getInt("id");
			}
			if(totaltime == -1) {
				ps = conn.prepareStatement("INSERT INTO `"+plugin.config.getTable()+"` (day, player) values (?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, date);
				ps.setString(2, player);
				 ps.executeUpdate();
				 rs = ps.getGeneratedKeys();
				 rs.next();
				 id = rs.getInt(1);
			}
			rs.close();
			ps.close();
			plugin.addToLoggingPlayers(player, id, totaltime, new Date().getTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stopLogging(String player) {
		int id = plugin.getId(player);
		int totaltime = (int)plugin.removeFromLoggingPlayers(player);
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE `"+plugin.config.getTable()+"` SET totaltime=? WHERE id=? ");
			ps.setInt(1, totaltime);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTables() {
		String sql = "CREATE TABLE IF NOT EXISTS `"+plugin.config.getTable()+"` (`id`  int NOT NULL AUTO_INCREMENT ,`player`  varchar(15) NOT NULL , `day` varchar(10) NOT NULL, `totaltime` int NOT NULL DEFAULT 0, PRIMARY KEY (`id`));";
		try{
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
