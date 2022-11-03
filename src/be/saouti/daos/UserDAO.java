package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import be.saouti.models.Administrator;
import be.saouti.models.Player;
import be.saouti.models.User;

public class UserDAO extends DAO<User>{
	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User user){
		if(user instanceof Player) 
			return new PlayerDAO(this.connect).create((Player)user);
		if(user instanceof Administrator) 
			return new AdministratorDAO(this.connect).create((Administrator)user);
		return false;
	}

	@Override
	public boolean delete(User user) {
		if(user instanceof Player) 
			return new PlayerDAO(this.connect).delete((Player)user);
		if(user instanceof Administrator) 
			return new AdministratorDAO(this.connect).delete((Administrator)user);
		return false;
	}

	@Override
	public boolean update(User user) {
		if(user instanceof Player) 
			return new PlayerDAO(this.connect).update((Player)user);
		if(user instanceof Administrator) 
			return new AdministratorDAO(this.connect).update((Administrator)user);
		return false;
	}

	@Override
	public User find(int id) {
		Player player = new PlayerDAO(this.connect).find(id);
		return (player != null) 
				?  (Player)player : (Administrator)new AdministratorDAO(this.connect).find(id);
	}
	
	public User find(String username) {
		Player player = new PlayerDAO(this.connect).find(username);
		return (player != null) 
				?  (Player)player : (Administrator)new AdministratorDAO(this.connect).find(username);
	}
}
