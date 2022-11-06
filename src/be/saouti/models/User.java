package be.saouti.models;
import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.UserDAO;

public abstract class User {
	//Members
	
	private int id = 0;
	private String username;
	private String password;
	
	//Getters and Setters
	
	public int getId() { return id; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	
	public void setId(int id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	
	//Constructor
	
	public User() {}
	public User(int id,String username, String password){
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	//Methods
	//Static
	public static User login(String username, String password){
		User user = new UserDAO(VideoGamesConnection.getInstance()).find(username);
		if(user != null && username.equals(user.getUsername()) && password.equals(user.getPassword()))
			return user;
		return null;
	}
}
