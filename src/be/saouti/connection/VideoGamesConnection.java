package be.saouti.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class VideoGamesConnection {

	private static Connection instance = null;
	
	private VideoGamesConnection(){
		try{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			String url = "jdbc:ucanaccess://./VideoGames.accdb";
			instance = DriverManager.getConnection(url);
		}
		catch(ClassNotFoundException ex){
			JOptionPane.showMessageDialog(null, "Driver Class not found: " + ex.getMessage());
			System.exit(0);
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "JDBC ERROR: " + ex.getMessage());
		}
		if (instance == null) {
            JOptionPane.showMessageDialog(null, "Connection failed, closing program...");
            System.exit(0);
        }
	}
	
	public static Connection getInstance() {
		if(instance == null){
			new VideoGamesConnection();
		}
		return instance;
	}

}
