package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.saouti.models.Copy;

public class CopyDAO implements ICopyDAO{
	protected Connection connect = null;
	
	public CopyDAO(Connection conn){
		this.connect = conn;
	}
	
	@Override
	public boolean delete(Copy copy) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "DELETE FROM copy WHERE copy_id = ?"
	        );
			prepare.setInt(1, copy.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Copy find(int id) {
		try {
			Copy copy = null;
			PreparedStatement prepare = connect.prepareStatement(
	            "SELECT * FROM copy WHERE copy_id = ?"
	        );
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.next()){
				copy = new Copy(
					id,
					new PlayerDAO(this.connect).find(result.getInt("player_id")),
					new VideoGameDAO(this.connect).find(result.getInt("videogame_id"))
				);
				PreparedStatement prepare1 = connect.prepareStatement(
		            "SELECT * FROM loan WHERE copy_id = ? AND ongoing = 1"
		        );
				prepare1.setInt(1, id);
				ResultSet result1 = prepare1.executeQuery();
				if(result1.next()){
					copy.borrow(
						result1.getInt("loan_id"), 
						new java.sql.Date(result1.getDate("start_date").getTime()).toLocalDate(),
						new java.sql.Date(result1.getDate("end_date").getTime()).toLocalDate(),
						new PlayerDAO(this.connect).find(result.getInt("player_id"))
					);
				}
			}
			return copy;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
