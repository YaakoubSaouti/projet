package be.saouti.models;

import java.util.regex.Pattern;

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
	public void setUsername(String username) throws Exception{ 
		if(!Pattern.matches("^[A-Za-z][A-Za-z0-9_]{2,29}$", username)) 
			throw new Exception(
					 "The username should be between 2 and 30 character,"
					+"\nshould start whith a letter and can only contain letters,numbers and '_'!");  
		this.username = username;
	}
	public void setPassword(String password) throws Exception{ 
		if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[=@$!%*#?&])[A-Za-z\\d=@$!%*#?&]{8,}$", password)) 
			throw new Exception(
					 "The password should be at least 8 characters "
					+"\nand should contain a letter,a number and a special character(=@$!%*#?&)");  
		this.password = password;
	}
	
	//Constructor
	
	public User() {}
	public User(int id,String username, String password){
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	//Methods
	public boolean login(String username, String password){
		return (this.username.equals(username) && this.password.equals(password));
	}
}
