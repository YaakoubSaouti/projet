package be.saouti.daos;

import java.sql.Connection;
import be.saouti.models.Administrator;
import be.saouti.models.Player;
import be.saouti.models.User;

public class UserDAO implements IUserDAO{
	protected Connection connect = null;
	
	public UserDAO(Connection conn){
		this.connect = conn;
	}
	
	@Override
	public User find(String username) {
		Player player = new PlayerDAO(this.connect).find(username);
		return (player != null) 
				?  (Player)player : (Administrator)new AdministratorDAO(this.connect).find(username);
	}
}
