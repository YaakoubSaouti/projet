package be.saouti.daos;

import java.sql.*;
import java.time.format.DateTimeFormatter;

import be.saouti.models.Player;

public class PlayerDAO extends DAO<Player>{

	public PlayerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Player player){
		try {
	        connect.setAutoCommit(false);
			PreparedStatement prepare1 = this.connect.prepareStatement(
	            "INSERT INTO user(username,password) VALUES (?,?)"
	        );
			prepare1.setString(1, player.getUsername());
			prepare1.setString(2, player.getPassword());
			prepare1.executeUpdate();
			ResultSet result = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
			.executeQuery("SELECT @@IDENTITY FROM user");
			if(result.first()) {
				int last_id = result.getInt(1); 
				PreparedStatement prepare2 = this.connect.prepareStatement(
		            "INSERT INTO player(user_id,pseudo,reg_date,dob,credit) VALUES (?,?,?,?,?)"
		        );
				prepare2.setInt(1, last_id);
				prepare2.setString(2, player.getPseudo());
				prepare2.setString(3, player.getRegistrationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				prepare2.setString(4, player.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				prepare2.setInt(5, player.getCredit());
				prepare2.executeUpdate();
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
	public boolean delete(Player player) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "DELETE * FROM user WHERE user_id = ?"
	        );
			prepare.setInt(1, player.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Player player) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "UPDATE user t1, player t2 "
	    		+"SET t1.username = ?, t1.password, "
	    		+"t2.pseudo = ?, t2.reg_date = ?, "
	    		+"t2.dob = ?, t2.credit = ? "
	    		+"WHERE t1.id = ? and t2.id = ?"
	        );
			prepare.setString(1, player.getUsername());
			prepare.setString(2, player.getPassword());
			prepare.setString(3, player.getPseudo());
			prepare.setString(4, player.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			prepare.setString(5, player.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			prepare.setInt(6, player.getCredit());
			prepare.setInt(8, player.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Player find(int id) {
		Player player = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
                "SELECT * FROM user,player WHERE user_id = ? AND user.user_id = player.user_id"
            );
			prepare.setInt(1,id);
			ResultSet result = prepare.executeQuery();
			if(result.next()) 
				player = new Player(
					id,
					result.getString("username"),
					result.getString("password"),
					result.getInt("credit"),
					result.getString("pseudo"),
					new java.sql.Date(result.getDate("reg_date").getTime()).toLocalDate(), 
					new java.sql.Date(result.getDate("dob").getTime()).toLocalDate()
				);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return player;
	}

	public Player find(String username){
		Player player = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
                "SELECT * FROM user,player WHERE username = ? AND user.user_id = player.user_id"
            );
			prepare.setString(1,username);
			ResultSet result = prepare.executeQuery();
			if(result.next()) 
				player = new Player(
					result.getInt("user_id"),
					result.getString("username"),
					result.getString("password"),
					result.getInt("credit"),
					result.getString("pseudo"),
					new java.sql.Date(result.getDate("reg_date").getTime()).toLocalDate(), 
					new java.sql.Date(result.getDate("dob").getTime()).toLocalDate()
				);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return player;
	}
}