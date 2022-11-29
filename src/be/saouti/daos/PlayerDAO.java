package be.saouti.daos;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import be.saouti.models.Loan;
import be.saouti.models.Player;

public class PlayerDAO implements IPlayerDAO{

	protected Connection connect = null;
	
	public PlayerDAO(Connection conn){
		this.connect = conn;
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
	public boolean update(Player player) {
		try {
	        connect.setAutoCommit(false);
			PreparedStatement prepare1 = this.connect.prepareStatement(
					 "UPDATE user "
					+"SET username = ?, password = ? "
		    		+"WHERE user_id = ?"
	        );
			prepare1.setString(1, player.getUsername());
			prepare1.setString(2, player.getPassword());
			prepare1.setInt(3, player.getId());
			prepare1.executeUpdate();
			PreparedStatement prepare2 = this.connect.prepareStatement(
					 "UPDATE player "
					+"SET "
		    		+"pseudo = ?, reg_date = ?, "
		    		+"dob = ?, credit = ? "
		    		+"WHERE user_id = ?"
    		);
			prepare2.setString(1, player.getPseudo());
			prepare2.setString(2, player.getRegistrationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			prepare2.setString(3, player.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			prepare2.setInt(4, player.getCredit());
			prepare2.setInt(5, player.getId());
			prepare2.executeUpdate();
			for(Loan l : player.getLended()) {
				PreparedStatement prepare3 = this.connect.prepareStatement(
					"UPDATE loan SET ongoing = ?"
		        );
				prepare3.setBoolean(1, l.isOngoing());
			}
			for(Loan b : player.getBorrowed()) {
				if(b.getId() == 0) {
					PreparedStatement prepare4 = this.connect.prepareStatement(
						"INSERT INTO loan(start_date,end_date,ongoing,borrower_id,copy_id) VALUES (?,?,?,?,?)"
			        );
					prepare4.setString(1, b.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));	
					prepare4.setString(2, b.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					prepare4.setBoolean(3, b.isOngoing());
					prepare4.setInt(4, b.getBorrower().getId());
					prepare4.setInt(5, b.getCopy().getId());
				}
			}
			player = find(player.getId());
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
			try{
				connect.setAutoCommit(true);
			} catch (SQLException e) {
				return false;
			}
		}
	}
	
	@Override
	public Player find(String username){
		Player player = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
                "SELECT * FROM user,player WHERE username = ? AND user.user_id = player.user_id"
            );
			prepare.setString(1,username);
			ResultSet result = prepare.executeQuery();
			if(result.next()) 
				player = find(result.getInt(1));
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return player;
	}

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