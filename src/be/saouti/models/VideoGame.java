package be.saouti.models;

import java.util.List;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.VideoGameDAO;

public class VideoGame {
	//Members
	private int id;
	private String name;
	private String console;
	private int creditCost;
	
	//Constructor
	public VideoGame(int id, String name, String console, int creditCost) {
		 this.id = id;
		 this.name = name;
		 this.console = console;
		 this.creditCost = creditCost;
	}
	//Getters and Setters
	public int getId() { return id; }
	public String getName() { return name; }
	public String getConsole() { return console; }
	public int getCreditCost() { return creditCost; }
	
	public void setId(int id) { this.id = id; }
	public void setConsole(String console) { this.console = console; }
	public void setName(String name) { this.name = name; }
	public void setCreditCost(int creditCost) throws Exception{ 
		if(creditCost<1) throw new Exception("The credit cost of a video game should be above 0");
		this.creditCost = creditCost;
	}
	
	//Methods
	//Static
	public static List<VideoGame> getAll(){
		return new VideoGameDAO(VideoGamesConnection.getInstance()).getAll();
	}
	public static VideoGame getVideoGame(int id) {
		return new VideoGameDAO(VideoGamesConnection.getInstance()).find(id);
	}
	//Non Static
	public boolean update(){
		return new VideoGameDAO(VideoGamesConnection.getInstance()).update(this);
	}
}
