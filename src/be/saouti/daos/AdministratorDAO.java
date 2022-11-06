package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import be.saouti.models.Administrator;
import be.saouti.models.Player;

public class AdministratorDAO implements IAdministratorDAO{
	protected Connection connect = null;
	
	public AdministratorDAO(Connection conn){
		this.connect = conn;
	}
	
	public Administrator find(String username){
		Administrator admin = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
                "SELECT * FROM user,administrator WHERE username = ? AND user.user_id = administrator.user_id"
            );
			prepare.setString(1,username);
			ResultSet result = prepare.executeQuery();
			if(result.next()) 
				admin = new Administrator(
				result.getInt("user_id"),
				result.getString("username"),
				result.getString("password")
			);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return admin;
	}
}
