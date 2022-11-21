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
			}
			return copy;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
