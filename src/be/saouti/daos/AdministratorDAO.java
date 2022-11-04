package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import be.saouti.models.Administrator;
import be.saouti.models.Player;

public class AdministratorDAO extends DAO<Administrator>{
	public AdministratorDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Administrator admin){
		try {
	        connect.setAutoCommit(false);
			PreparedStatement prepare1 = this.connect.prepareStatement(
	            "INSERT INTO user(username,password) VALUES (?,?)"
	        );
			prepare1.setString(1, admin.getUsername());
			prepare1.setString(2, admin.getPassword());
			prepare1.executeUpdate();
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
			.executeQuery("SELECT @@IDENTITY FROM user");
			if(result.first()) {
				int last_id = result.getInt(1); 
				PreparedStatement prepare2 = this.connect.prepareStatement(
		            "INSERT INTO player(user_id) VALUES (?)"
		        );
				prepare2.setInt(1, last_id);
			}
	        connect.commit();
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			try {
				connect.rollback();
			} catch (SQLException e1) {
				return false;
			}
			return false;
		}finally {
			try {
				connect.setAutoCommit(true);
			} catch (SQLException e) {
				return false;
			}
		}
	}
	
	@Override
	public boolean delete(Administrator admin) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "DELETE * FROM user WHERE user_id = ?"
	        );
			prepare.setInt(1, admin.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Administrator admin) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "UPDATE user"
	    		+"SET username = ?, password = ?"
	    		+" WHERE id = ?"
	        );
			prepare.setString(1, admin.getUsername());
			prepare.setString(2, admin.getPassword());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Administrator find(int id) {
		Administrator admin = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
                "SELECT * FROM user,administrator WHERE user_id = ? AND user.user_id = administrator.user_id"
            );
			prepare.setInt(1,id);
			ResultSet result = prepare.executeQuery();
			if(result.next()) 
				admin = new Administrator(
					id,
					result.getString("username"),
					result.getString("password")
				);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return admin;
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
